# obd2_logger.py:
# Sends OBD2 messages to the Android app via Bluetooth.

from obd2_supported import *
import obd2_stats


# Halts program execution until the CAN bus becomes responsive to OBD2 messages.
def wait_for_bus_response():
    while not is_bus_responsive():
        pass
    return


# Transmits one block of stats over Bluetooth
def transmit_stats(stats):
    print(stats)
    pass  # TODO: implement bluetooth


# Logs and transmits OBD2 statistics until the CAN bus stops responding
def obd2_log_until_bus_stops_responding():
    supported_pids = find_all_supported()
    while is_bus_responsive():
        stat_snapshot = obd2_stats.get_stats(obd2_stats.DESRIRED_PIDS, supported_pids)
        transmit_stats(stat_snapshot)


# Continually logs OBD2 statistics and enters an idle state while the CAN bus is unresponsive
def obd2_log():
    while True:
        wait_for_bus_response()
        obd2_log_until_bus_stops_responding()


if __name__ == "__main__":
    obd2_log()
