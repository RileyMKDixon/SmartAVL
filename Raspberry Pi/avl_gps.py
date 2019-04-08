# Author: Riley Dixon
#This class is adapted from Adafruit and the example code that
#they provide to their users for their hardware products.
#This script relies on their CircuitPython framework that the GPS uses
#to communicate through the GPIO pins or USB of the Raspberry Pi.
#Communication is handled through the UART
#More information can be found here: https://github.com/adafruit/Adafruit_CircuitPython_GPS

import time
import serial
import adafruit_gps
import os
import threading


class SmartAVLGPS(threading.Thread):
	
	#set connection_type = 0 for GPIO Pins
	#set connection_type = 1 for USB connection
	#all other values will raise an error
	#this may only be set at the creation of the object.
	def __init__(self, connection_type):
		if connection_type not in [0, 1]:
			raise ValueError("Connection Type must be 0 for Serial or 1 for USB")
		threading.Thread.__init__(self)
		self.connection_type = connection_type #Is the connection made via GPIO pins or USB
		self.data_semaphore = threading.BoundedSemaphore(1) #Allow controled access to the data
		self.current_latitude = None
		self.current_longitude = None
		self.current_speed = None
		self.timestamp = None #A time library structure
		self.gps = None #The GPS object as designed by Adafruit.
		
	def run(self):
		#Consider wrapping in a try-except block to reset the GPS.
		self.connect_to_GPS_network()
		while(True):
			update_present = self.gps.update()
			if update_present:
				if self.gps.has_fix:
					self.update_data()
				else:
					print("No fix on GPS network")
					#We won't invalidate the location data, instead the timestamp will just remain old
			else:
				print("No update received.")
			
			#Enforce a 0.5s delay before next update. This is half of
			#the update period, as requested by the adafruit documentation.
			time_before_sleep = time.monotonic()
			while(time.monotonic() - time_before_sleep < 0.5):
				time.sleep(0.1) #Let the thread block instead of busy wait
		
		
	def connect_to_GPS_network(self):
		if (self.connection_type == 0):
			#The serial RX, TX pins make up this port.
			UART = serial.Serial("/dev/ttyS0", baudrate=9600, timeout=3000)
		elif (self.connection_type == 1):
			#Connects to the device by ID, incase ttyUSB0 already used by a
			#different device.
			UART = serial.Serial("/dev/serial/by-id/usb-Silicon_Labs_CP2102_USB_to_UART_Bridge_Controller_0001-if00-port0", baudrate=9600, timeout=3000)
		else:
			raise ValueError("Connection Type must be 0 for Serial or 1 for USB")
		self.gps = adafruit_gps.GPS(UART, debug=False)
		
		#Initialize Communication
		self.gps.send_command(b'PMTK314,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0')
		self.gps.send_command(b'PMTK220,1000') #1000ms update period

	#The GPS returns it's estimated speed in knots. Let's convert this to km/h
	def knots_to_kmh(self, knots):
		return knots * 1.852
	
	#Compare the passed timestamp to the one currently stored in object
	#If passed timestamp is newer, return 1
	#If passed timestamp is the same, return 0
	#If passed timestamp is older, return -1
	#Else return None as result is inconclusive (should not happen)
	def compare_timestamp(self, other_timestamp):
		result = None
		if(other_timestamp.tm_year == self.timestamp.tm_year):
			if(other_timestamp.tm_month == self.timestamp.tm_month):
				if(other_timestamp.tm_mday == self.timestamp.tm_mday):
					if(other_timestamp.tm_hour == self.timestamp.tm_hour):
						if(other_timestamp.tm_min == self.timestamp.tm_min):
							if(other_timestamp.tm_sec == self.timestamp.tm_sec):
								result = 0
							elif(other_timestamp.tm_sec > self.timestamp.tm_sec):
								result = 1
							else:
								result = -1
						elif(other_timestamp.tm_min > self.timestamp.tm_min):
							result = 1
						else:
							result = -1
					elif(other_timestamp.tm_hour > self.timestamp.tm_hour):
						result = 1
					else:
						result = -1
				elif(other_timestamp.tm_mday > self.timestamp.tm_mday):
					result = 1
				else:
					result = -1
			elif(other_timestamp.tm_mon > self.timestamp.tm_mon):
				result = 1
			else:
				result = -1
		elif(other_timestamp.tm_year > self.timestamp.tm_year):
			result = 1
		else:
			result = -1
		return result
		
	#Update the data contained in the object in a controlled fashion.
	#We will only update the data so long as it is not currently be read
	#By an outside accessor. This will ensure data is consistent and not
	#in between states.
	def update_data(self):
		self.data_semaphore.acquire()
		self.current_latitude = self.gps.latitude
		self.current_longitude = self.gps.longitude
		self.current_speed = self.gps.speed_knots
		self.timestamp = self.gps.timestamp_utc
		self.data_semaphore.release()
	
	#Return the data points in an independent list. The object data
	#is marshalled so that its references are not changed by a GPS update.
	#This eliminates the race condition of having to process data before it
	#is refreshed on gps.update().
	#
	#This is returned as a list to get all of the data pertaining to the
	#specific update interval.
	def get_data(self):
		import copy
		self.data_semaphore.acquire()
		if (self.timestamp is not None and
		   self.current_latitude is not None and
		   self.current_longitude is not None and
		   self.current_speed is not None):
			data_list = [copy.deepcopy(self.timestamp), 
						copy.deepcopy(self.current_latitude),
						copy.deepcopy(self.current_longitude),
						copy.deepcopy(self.current_speed)]
		else:
			data_list = None
		self.data_semaphore.release()
		return data_list
		




