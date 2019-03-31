# canlog.py:
# Logs serialized CAN messages to a local file.

import can
import json
from canencoder import CANEncoder

log_file = "can.log"

bus = can.interface.Bus()


def setup_bus_filters():
    obd2_filters = [
        {
            "can_id": 0x7df,
            "can_mask": 0x7ff
        },
        {
            "can_id": 0x7e8,
            "can_mask": 0x7ff
        },
    ]
    bus.set_filters(obd2_filters)


def log_msg_local(msg):
    with open(log_file, "a+") as f:
        j = json.dumps(msg, cls=CANEncoder)
        f.write(j + "\n")


def log_msgs():
    while True:
        msg = bus.recv()
        log_msg_local(msg)


if __name__ == "__main__":
    setup_bus_filters()
    log_msgs()
