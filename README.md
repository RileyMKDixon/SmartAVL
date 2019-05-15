# SmartAVL
ECE 492 Capstone Project - Smart Automatic Vehicle Locator (Smart AVL) | University of Alberta, Winter 2019


The project that I worked on for the capstone course for my degree. My contributions to the project were the Bluetooth module and the GPS
module, both of which run on their own seperate threads.

SmartAVL is a system that works with a Raspberry Pi in conjunction with an Android Phone to track a vehicles location and diagnostic data.
The Raspberry Pi is connected the the vehicle via the OBD-2 port and polls for diagnostic data off of it. This data is then serialized into
a JSON format which is then forwarded via Bluetooth to the Android phone where the data is presented in a User Friendly format.

Please take a look at the final report and poster for more details, both of which are contained in the "docs" directory.
