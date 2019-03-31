# cansupported.py:
# Determines which OBD2 PIDs are supported by a particular vehicle.
# (Default bus is specified in python-can configuration file)

import can
import time

OBD2_REQUEST = 0x7df
OBD2_RESPONSE = 0x7e8

bus = can.interface.Bus()  # default bus


# Sets up filters on the default bus to only receive responses to OBD2 requests.
def setup_bus_filters():
    obd2_filters = [
        {
            "can_id": OBD2_RESPONSE,
            "can_mask": 0x7f8
        }
    ]
    bus.set_filters(obd2_filters)


# Sends an OBD2 request to the CAN bus.
def send_request(pid):
    # create CAN message data from PID. 0x55 bytes are filler.
    data = [0x02, 0x01, pid, 0x55, 0x55, 0x55, 0x55, 0x55]

    msg = can.Message(arbitration_id=OBD2_REQUEST, data=data, is_extended_id=False)
    bus.send(msg)

    time.sleep(0.3)  # a delay to ensure the CAN bus can respond to each message


# Gets supported PIDs from a "supported" message (PIDs 0x00, 0x20, 0x40...) and returns them in a list.
def get_pids_from_msg(msg, msg_pid):
    if msg.arbitration_id != OBD2_RESPONSE:
        raise ValueError('CAN message arbitration ID is {0} (should be {1})'.format(msg.arbitration_id, OBD2_RESPONSE))
    if msg.data[2] != msg_pid:
        raise ValueError('CAN message PID is {0} (should be {1})'.format(msg.data[2], msg_pid))

    encoded_pids = int.from_bytes(msg.data[3:7], byteorder='big')
    decoded_pids = []
    for i in range(0x1f, -1, -1):
        mask = 1 << i
        if encoded_pids & mask:
            pid = 0x20 - i + msg_pid
            decoded_pids.append(pid)

    return decoded_pids


# Finds supported OBD2 PIDs and returns them in a list.
def find_supported():
    supported_pids = []
    for query_pid in range(0x00, 0xE0, 0x20):
        send_request(query_pid)

        msg = bus.recv(0.05)
        if msg is None:
            continue

        pids = get_pids_from_msg(msg, query_pid)
        for p in pids:
            supported_pids.append(p)

    return supported_pids


if __name__ == "__main__":
    setup_bus_filters()
    supported = find_supported()
    print("Supported PIDs:", supported)
