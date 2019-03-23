# cansend.py:
# Sends 1 hardcoded message to the default CAN bus.
# (Default bus is specified in python-can configuration file)

import can
import time

def send_msg():
    bus = can.interface.Bus() # default bus
    msg = can.Message(arbitration_id=0x7df, data=[0x02, 0x01, 0x0D, 0x55, 0x55, 0x55, 0x55, 0x55], is_extended_id=False)
    bus.send(msg)

if __name__ == "__main__":
    send_msg()
