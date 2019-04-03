# obd2_supported.py:
# Determines which OBD2 PIDs are supported by a particular vehicle.

from obd2_utils import *


# Gets supported PIDs from a "supported" message (PIDs 0x00, 0x20, 0x40...) and returns them in a list.
def get_supported_from_msg(msg, msg_pid):
    if msg.data[2] != msg_pid:
        raise ValueError('CAN message PID is {0} (should be {1})'.format(msg.data[2], msg_pid))

    encoded = int.from_bytes(msg.data[3:7], byteorder='big')
    decoded = []
    for i in range(0x1f, -1, -1):
        mask = 1 << i
        if encoded & mask:
            pid = 0x20 - i + msg_pid
            decoded.append(pid)

    return decoded


# Finds supported OBD2 PIDs across all ranges and returns them in a list.
def find_all_supported():
    all_supported = []
    for query_pid in range(0x00, 0xE0, 0x20):
        send_obd2_request(query_pid)
        msg = get_obd2_response()

        if msg is not None:
            supported = get_supported_from_msg(msg, query_pid)
            for p in supported:
                all_supported.append(p)

    return all_supported


# Displays all supported OBD2 PIDs to the terminal
def display_all_supported():
    supported = find_all_supported()
    if supported:
        print("The vehicle supports the following OBD-II PIDs:")
        print(", ".join(hex(pid) for pid in supported))
    else:
        print("The vehicle appears not to support any OBD-II PIDs.")
        print("Check if the engine is running.")


if __name__ == "__main__":
    display_all_supported()

