# Unit tests for cansupported.py

import unittest
import can
import sys
sys.path.append("..")
import cansupported


class TestCanSupported(unittest.TestCase):
    def test_read_pids_from_msg(self):
        msg_pid = 0x00
        msg = can.Message(arbitration_id=cansupported.OBD2_RESPONSE,
                          data=[0x06, 0x41, msg_pid, 0xbe, 0x1f, 0xa8, 0x13, 0x00])
        decoded_pids = cansupported.get_pids_from_msg(msg, msg_pid)
        self.assertTrue(decoded_pids == [1, 3, 4, 5, 6, 7, 12, 13, 14, 15, 16, 17, 19, 21, 28, 31, 32])

    def test_read_pids_from_msg_wrong_arb_id(self):
        msg_pid = 0x00
        msg = can.Message(arbitration_id=0x123,
                          data=[0x06, 0x41, msg_pid, 0xbe, 0x1f, 0xa8, 0x13, 0x00])
        self.assertRaises(ValueError, cansupported.get_pids_from_msg, msg, msg_pid)

    def test_read_pids_from_msg_wrong_msg_pid(self):
        msg_pid = 0x20
        msg = can.Message(arbitration_id=cansupported.OBD2_RESPONSE,
                          data=[0x06, 0x41, 0x00, 0xbe, 0x1f, 0xa8, 0x13, 0x00])
        self.assertRaises(ValueError, cansupported.get_pids_from_msg, msg, msg_pid)


if __name__ == "__main__":
    unittest.main()