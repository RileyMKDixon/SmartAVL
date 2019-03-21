package ece492.smartavl.bluetooth;

import android.bluetooth.BluetoothAdapter;

public class BluetoothWrapper {

    private static BluetoothAdapter bluetoothAdapter;
    private static CommHandler commHandler;
    private static BTDeviceData connectedDevice;

    public static void setBluetoothAdapter(BluetoothAdapter adapter) {
        bluetoothAdapter = adapter;
    }

    public static BluetoothAdapter getBluetoothAdapter() {
        return bluetoothAdapter;
    }

    public static void setCommHandler(CommHandler handler) {
        commHandler = handler;
    }

    public static CommHandler getCommHandler() {
        return commHandler;
    }

    public static void setConnectedDevice(BTDeviceData device) {
        connectedDevice = device;
    }

    public static BTDeviceData getConnectedDevice() {
        return connectedDevice;
    }

}
