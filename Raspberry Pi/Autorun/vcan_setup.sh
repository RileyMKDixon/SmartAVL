#!/usr/bin/env bash

sudo modprobe vcan
sudo ip link add dev vcan0 type vcan
sudo ip link set vcan0 up
sudo socat pty,link=/dev/ttyUSB0,raw,echo=0,waitslave INTERFACE:vcan0 &
