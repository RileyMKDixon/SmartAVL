package ece492.smartavl.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Handler;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class BluetoothWrapper {

    public static BluetoothAdapter bluetoothAdapter;
    public static int numPairedDevices;
    public static BTDeviceData connectedDevice;

    public static ArrayList<BTDeviceData> btDevices;
    public static ArrayAdapter<BTDeviceData> btDeviceListAdapter;

    public static CommHandler commHandler;
    public static Handler handler;

    /**
     * Attempts to set up the Bluetooth connection to the Raspberry Pi
     * @return Success or error code
     */
    public static int attemptSetup(Context context) {
        // TODO
        // This should be called from within the Splash Screen activity
        // Return a status code which we can use to display error messages to the user if something goes wrong
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        numPairedDevices = 0;
        connectedDevice = null;

        return 0;
    }

}
