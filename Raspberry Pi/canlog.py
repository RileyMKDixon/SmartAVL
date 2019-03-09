# canlog.py:
# Logs serialized CAN messages to a local file.

import can
import json
from canencoder import CANEncoder

log_file = "can.log"

def log_msg_local(msg):
    with open(log_file, "a+") as f:
        j = json.dumps(msg, cls=CANEncoder)
        f.write(j + "\n")

def log_msgs():
    bus = can.interface.Bus()
    while True:
        msg = bus.recv()
        log_msg_local(msg)

if __name__ == "__main__":
    log_msgs()