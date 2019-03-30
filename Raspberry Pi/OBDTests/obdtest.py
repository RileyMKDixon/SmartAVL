import obd
obd.logger.setLevel(obd.logging.DEBUG) # enables debug info

conn = obd.OBD("/dev/ttyUSB0", 500000, fast=False)
print("Connection initialized!")
print("Protocol: ", conn.protocol_name())


if conn.is_connected():
    print("Success!")
else:
    print("Failure.")
