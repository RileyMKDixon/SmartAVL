package ece492.smartavl.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import ece492.smartavl.data.VehicleData;

public class BluetoothWrapper {

    private static BluetoothAdapter bluetoothAdapter;
    private static BluetoothSocket bluetoothSocket;
    private static CommHandler commHandler;
    private static BTDeviceData connectedDevice;
    private static Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message inputMessage){
            int whatHappened = inputMessage.what;
            switch (whatHappened){
                case CommHandler.MESSAGE_READ:
                    break;
                case CommHandler.MESSAGE_WRITE:
                    break;
                case CommHandler.CONNECTION_FAILED:
                    VehicleData.BLUETOOTH_STATUS = VehicleData.BLUETOOTH_CONNECTION_FAILED;
            }

        }
    };

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

    public static BluetoothSocket getBluetoothSocket() {
        return bluetoothSocket;
    }

    public static void setBluetoothSocket(BluetoothSocket bluetoothSocket) {
        BluetoothWrapper.bluetoothSocket = bluetoothSocket;
    }

    public static Handler getHandler(){
        return handler;
    }
}
