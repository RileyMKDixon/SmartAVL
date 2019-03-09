# canspam.py:
# Spams messages to the default CAN bus.
# (Default bus is specified in python-can configuration file)

import can
import time

def send_msg(id):
    bus = can.interface.Bus() # default bus
    msg = can.Message(arbitration_id=id, data=[1, 2, 3, 4, 5, 6, 7, 8], is_extended_id=False)
    bus.send(msg)
    
def spam_msgs():
    while True:
        for i in range(0x7ff):
            send_msg(i)
            time.sleep(0.1)

if __name__ == "__main__":
    spam_msgs()
