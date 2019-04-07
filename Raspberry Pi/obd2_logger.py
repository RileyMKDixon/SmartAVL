# obd2_logger.py:
# Sends OBD2 messages to the Android app via Bluetooth.

from obd2_supported import *
from bt_server import BluetoothServer
import obd2_stats
import time
import json

bt = BluetoothServer()


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
        transmit_stats(stat_snapshot)


# Continually logs OBD2 statistics and enters an idle state while the CAN bus is unresponsive
def obd2_log():
    while True:
        wait_for_bus_response()
        obd2_log_until_bus_stops_responding()


if __name__ == "__main__":
    start_bluetooth_server()
    obd2_log()
