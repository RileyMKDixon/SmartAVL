# obd2_utils.py:
# Provides utility functions for sending and receiving OBD2 messages to a CAN bus.
# (Default bus is specified in python-can configuration file)

import can

CAN_TIMEOUT = 0.05

OBD2_REQUEST_ID = 0x7df
OBD2_RESPONSE_ID = 0x7e8

bus = can.interface.Bus()  # default bus
obd2_filters = [
    {
        "can_id": OBD2_RESPONSE_ID,
        "can_mask": 0x7f8
    }
]
bus.set_filters(obd2_filters)


# Sends an OBD2 request to the CAN bus.
def send_obd2_request(pid):
    # create CAN message data from PID. 0x55 bytes are filler.
    data = [0x02, 0x01, pid, 0x55, 0x55, 0x55, 0x55, 0x55]

    msg = can.Message(arbitration_id=OBD2_REQUEST_ID, data=data, is_extended_id=False)
    bus.send(msg)


# Receives an OBD2 response from the CAN bus.
def get_obd2_response():
    return bus.recv(CAN_TIMEOUT)  # any message received by the CAN bus is a valid OBD2 response as per filters
