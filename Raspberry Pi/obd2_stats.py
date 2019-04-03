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
PID_MONITOR_STATUS = 0x01


# Extracts relevant data from a CAN bus message given the OBD2 PID.
def extract_data(msg, pid):
    if pid == PID_SPEED:
        return extract_speed(msg)
    if pid == PID_RPM:
        return extract_rpm(msg)
    if pid == PID_FUEL_TANK_LEVEL:
        return extract_fuel_tank_level(msg)
    if pid == PID_FUEL_PRESSURE:
        return extract_fuel_pressure(msg)
    if pid == PID_OIL_TEMP:
        return extract_oil_temp(msg)
    if pid == PID_DISTANCE_SINCE_CLEAR:
        return extract_distance_since_clear(msg)
    if pid == PID_MONITOR_STATUS:
        return extract_monitor_status(msg)


# Extracts vehicle speed from a CAN message (PID 0x0D).
def extract_speed(msg):
    return int(msg.data[3])


# Extracts RPM from a CAN message (PID 0x0C).
def extract_rpm(msg):
    return int.from_bytes(msg.data[3:5], byteorder='big') / 4


# Extracts fuel tank level from a CAN message (PID 0x2F).
def extract_fuel_tank_level(msg):
    return 100 / 255 * int(msg.data[3])


# Extracts fuel pressure from a CAN message (PID 0x0A).
def extract_fuel_pressure(msg):
    return 3 * int(msg.data[3])


# Extracts engine oil temperature from a CAN message (PID 0x5C).
def extract_oil_temp(msg):
    return int(msg.data[3]) - 40


# Extracts distance since codes have been cleared from a CAN message (PID 0x31).
def extract_distance_since_clear(msg):
    return int.from_bytes(msg.data[3:5], byteorder='big')


# Extracts check engine light status from a CAN message (PID 0x01).
# Returns 1 if check engine light is on, and 0 otherwise.
def extract_monitor_status(msg):
    return int(msg.data[3]) >> 7


# Gets OBD2 statistics for a supplied list of PIDs.
# Returns a dictionary keyed by PID.
def get_stats(desired, supported):
    data = {}
    for pid in desired:
        if pid in supported:
            send_obd2_request(pid)
            msg = get_obd2_response()
            data[pid] = extract_data(msg, pid)

    return data


# Gets OBD2 statistics from the vehicle.
# Returns a dictionary keyed by PID.
def get_desired_stats():
    desired = [PID_SPEED, PID_RPM, PID_FUEL_TANK_LEVEL, PID_FUEL_PRESSURE,
               PID_OIL_TEMP, PID_DISTANCE_SINCE_CLEAR, PID_MONITOR_STATUS]
    supported = find_all_supported()
    return get_stats(desired, supported)


if __name__ == "__main__":
    print("Desired stats:", get_desired_stats())
