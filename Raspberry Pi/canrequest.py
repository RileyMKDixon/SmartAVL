# canrequest.py:
# Asks the CAN bus for a number of OBD-2 statistics.
# (Default bus is specified in python-can configuration file)

import can
import time

OBD2_REQUEST = 0x7df

def send_request(pid):
    # create CAN message data from PID. 0x55 bytes are filler.
    data = [0x02, 0x01, pid, 0x55, 0x55, 0x55, 0x55, 0x55]
    
    bus = can.interface.Bus() # default bus
    msg = can.Message(arbitration_id=OBD2_REQUEST, data=data, is_extended_id=False)
    bus.send(msg)
    
# Refer to https://en.wikipedia.org/wiki/OBD-II_PIDs#Service_01 for OBD2 PIDs
def make_requests():
    send_request(0x0D) # Vehicle speed (km/h)
    send_request(0x0C) # Engine RPM
    send_request(0x2F) # Fuel tank level (%)
    send_request(0x0A) # Fuel pressure (kPa)
    send_request(0x5C) # Engine oil temperature (C)
    send_request(0x31) # Distance travelled since codes cleared (km)
    send_request(0x01) # Monitor status
    
if __name__ == "__main__":
    make_requests()