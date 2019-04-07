# canencoder.py:
# Defines a CANEncoder class which is used to serialize a CAN
# message into a JSON string.

import json
import can

class CANEncoder(json.JSONEncoder):
    def default(self, obj):
        if isinstance(obj, can.Message):
            return {
                "timestamp": obj.timestamp,
                "arbitration_id": obj.arbitration_id,
                "data": obj.data,
                "dlc": obj.dlc,
                "channel": obj.channel,
                "is_extended_id": obj.is_extended_id,
                "is_error_frame": obj.is_error_frame,
                "is_remote_frame": obj.is_remote_frame,
                "is_fd": obj.is_fd,
                "bitrate_switch": obj.bitrate_switch,
                "error_state_indicator": obj.error_state_indicator
                }
        elif isinstance(obj, bytearray):
            # convert bytearray to list of int
            return [x for x in obj]
        return json.JSONEncoder.default(self, obj)
    
# Do tests if we're just running this file
if __name__ == "__main__":
    msg = can.Message(arbitration_id=0x111, data=[0, 1, 2, 3, 4, 5, 6, 7], is_extended_id=False)
    j = json.dumps(msg, cls=CANEncoder)
    print("Sample JSON-encoded CAN message:")
    print(j)