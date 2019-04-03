# Unit tests for obd2_stats.py

import unittest
import can
import sys
sys.path.append("..")
from obd2_stats import *


class TestOBD2Stats(unittest.TestCase):
    def test_extract_speed(self):
        msg = can.Message(arbitration_id=OBD2_RESPONSE_ID, data=[0x06, 0x41, PID_SPEED, 0x19, 0x00, 0x00, 0x00, 0x00])
        self.assertEqual(extract_speed(msg), 0x19)

    def test_extract_rpm(self):
        msg = can.Message(arbitration_id=OBD2_RESPONSE_ID, data=[0x06, 0x41, PID_RPM, 0x00, 0x00, 0x00, 0x00, 0x00])
        self.assertEqual(extract_rpm(msg), 0)


if __name__ == "__main__":
    unittest.main()
