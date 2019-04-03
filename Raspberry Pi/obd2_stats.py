# obd2_stats.py:
# Uses the OBD2 protocol to get statistics pertaining to the vehicle.

from obd2_utils import *
from obd2_supported import find_all_supported

PID_SPEED = 0x0D
PID_RPM = 0x0C
PID_FUEL_TANK_LEVEL = 0x2F
PID_FUEL_PRESSURE = 0x0A
PID_OIL_TEMP = 0x5C
PID_DISTANCE_SINCE_CLEAR = 0x31
PID_STATUS = 0x01


def extract_data(msg, pid):
    if pid == PID_SPEED:
        return extract_speed(msg)
    if pid == PID_RPM:
        return extract_rpm(msg)
    if pid == PID_FUEL_TANK_LEVEL:
        return extract_fuel_tank_level(msg)


def extract_speed(msg):
    return int(msg.data[3])


def extract_rpm(msg):
    return int.from_bytes(msg.data[3:5], byteorder='big') / 4


def extract_fuel_tank_level(msg):
    return 100 / 255 * int(msg.data[3])


def get_stats(desired):
    supported = find_all_supported()
    data = {}

    for pid in desired:
        if pid in supported:
            send_obd2_request(pid)
            msg = get_obd2_response()
            data[pid] = extract_data(msg, pid)

    return data


def get_desired_stats():
    desired = [PID_SPEED, PID_RPM]
    # desired = [PID_SPEED, PID_RPM, PID_FUEL_TANK_LEVEL, PID_FUEL_PRESSURE,
    #           PID_OIL_TEMP, PID_DISTANCE_SINCE_CLEAR, PID_STATUS]
    return get_stats(desired)


if __name__ == "__main__":
    print("Desired stats:", get_desired_stats())
