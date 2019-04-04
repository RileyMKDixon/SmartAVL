# Unit tests for obd2_stats.py

import unittest
import can
import sys
sys.path.append("..")
from obd2_stats import *


class TestOBD2Stats(unittest.TestCase):
    def test_extract_speed(self):
        msg = can.Message(arbitration_id=OBD2_RESPONSE_ID,
                          data=[0x06, 0x41, PID_SPEED, 0x19, 0x00, 0x00, 0x00, 0x00])
        self.assertEqual(extract_speed(msg), 0x19)

    def test_extract_rpm(self):
        msg = can.Message(arbitration_id=OBD2_RESPONSE_ID,
                          data=[0x06, 0x41, PID_RPM, 0x0c, 0xda, 0x00, 0x00, 0x00])
        self.assertEqual(extract_rpm(msg), 0xcda / 4)

    def test_extract_distance_since_clear(self):
        msg = can.Message(arbitration_id=OBD2_RESPONSE_ID,
                          data=[0x06, 0x41, PID_DISTANCE_SINCE_CLEAR, 0xff, 0x3a, 0x00, 0x00, 0x00])
        self.assertEqual(extract_distance_since_clear(msg), 0xff3a)

    def test_extract_monitor_status_light_off(self):
        msg = can.Message(arbitration_id=OBD2_RESPONSE_ID,
                          data=[0x06, 0x41, PID_MONITOR_STATUS, 0x00, 0x07, 0xe5, 0x00, 0x00])
        self.assertEqual(extract_monitor_status(msg), 0)

    def test_extract_monitor_status_light_on(self):
        msg = can.Message(arbitration_id=OBD2_RESPONSE_ID,
                          data=[0x06, 0x41, PID_MONITOR_STATUS, 0x80, 0x07, 0xe5, 0x00, 0x00])
        self.assertEqual(extract_monitor_status(msg), 1)


if __name__ == "__main__":
    unittest.main()
