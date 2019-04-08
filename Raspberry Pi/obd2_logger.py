# obd2_logger.py:
# Sends OBD2 messages to the Android app via Bluetooth.

from obd2_supported import *
from bt_server import BluetoothServer
from avl_gps import SmartAVLGPS
import obd2_stats
import time
import json

bt = BluetoothServer()
gps = SmartAVLGPS(1)


# Halts program execution until the GPS begins reporting data
def start_avl_gps():
    gps.start()
    while gps.get_data() is None:
        pass


# Returns latitude and longitude from GPS module
def get_coords_from_gps():
    d = gps.get_data()
    if d is not None:
        return {"lat": d[1], "long": d[2]}
    else:
        return {}


# Halts program execution until a Bluetooth connection is made
def start_bluetooth_server():
    bt.start()
    while not bt.isConnected:
        time.sleep(0.5)  # Bluetooth does not seem to like using "pass" here


# Halts program execution until the CAN bus becomes responsive to OBD2 messages.
def wait_for_bus_response():
    while not is_bus_responsive():
        pass
    return


# Transmits one block of stats over Bluetooth
def transmit_stats(stats):
    j = json.dumps(stats)
    bt.write(j)


# Logs and transmits OBD2 statistics until the CAN bus stops responding
def obd2_log_until_bus_stops_responding():
    supported_pids = find_all_supported()
    while is_bus_responsive():
        stat_snapshot = obd2_stats.get_stats(obd2_stats.DESIRED_PIDS, supported_pids)

        coords = get_coords_from_gps()
        for k in coords.keys():
            stat_snapshot[k] = coords[k]  # merge coords dict into stat_snapshot dict

        transmit_stats(stat_snapshot)


# Continually logs OBD2 statistics and enters an idle state while the CAN bus is unresponsive
def obd2_log():
    while True:
        wait_for_bus_response()
        obd2_log_until_bus_stops_responding()


# Test function with dummy data
def test_dummy_data():
    stat_snapshot = {
        0x0D: 0,
        0x0C: 885,
        0x01: 0
    }
    coords = get_coords_from_gps()
    for k in coords.keys():
        stat_snapshot[k] = coords[k]  # merge coords dict into stat_snapshot dict

    transmit_stats(stat_snapshot)


if __name__ == "__main__":
    start_avl_gps()
    start_bluetooth_server()
    # obd2_log()
    test_dummy_data()
