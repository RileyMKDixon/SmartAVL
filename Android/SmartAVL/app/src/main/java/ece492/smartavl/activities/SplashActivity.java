package ece492.smartavl.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import ece492.smartavl.R;
import ece492.smartavl.bluetooth.BTDeviceData;
import ece492.smartavl.bluetooth.BluetoothWrapper;
import ece492.smartavl.bluetooth.CommHandler;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {

    private static final int BLUETOOTH_NOT_INITIALIZED = -1;
    private static final int BLUETOOTH_SETUP_COMPLETE = 0;
    private static final int BLUETOOTH_NOT_SUPPORTED = 1;
    private static final int BLUETOOTH_NOT_ENABLED = 2;
    private static final int BLUETOOTH_NO_PAIRED_DEVICES = 3;

    private ProgressBar progressBar;
    private ProgressBarAnimation anim0;
    private ProgressBarAnimation anim1;
    private ProgressBarAnimation anim2;
    private Set<BluetoothDevice> pairedDevices;
    private ArrayList<BluetoothDevice> failedToConnectDevices;
    int SETUP_RESULT = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        progressBar = findViewById(R.id.SplashActivity_progressBar);
        anim0 = new ProgressBarAnimation(progressBar, 0, 33);
        anim1 = new ProgressBarAnimation(progressBar, 33, 66);
        anim2 = new ProgressBarAnimation(progressBar, 66, 100);


        BluetoothWrapper.setBluetoothAdapter(BluetoothAdapter.getDefaultAdapter());
        pairedDevices = BluetoothWrapper.getBluetoothAdapter().getBondedDevices();
        failedToConnectDevices = new ArrayList<BluetoothDevice>();


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        animateProgressBar(500);
    }

    private void animateProgressBar(int segmentDuration) {
        progressBar.setMax(100);
        Animation.AnimationListener animationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Do nothing
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (progressBar.getProgress() == progressBar.getMax()){
                    onProgressBarFinished();
                }else{
                    onProgressBarStopped(progressBar.getProgress());
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Do nothing
            }
        };
        anim0.setAnimationListener(animationListener);
        anim0.setDuration(segmentDuration);
        anim1.setAnimationListener(animationListener);
        anim1.setDuration(segmentDuration);
        anim2.setAnimationListener(animationListener);
        anim2.setDuration(segmentDuration);
        progressBar.startAnimation(anim0);
    }

    private void onProgressBarFinished() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void onProgressBarStopped(int progress) {
        // TODO: Trigger intermediate loading steps such as bluetooth searching, pairing, checking status, etc.
        if (progress == 33){
            loadingSection1();
        }else if (progress == 66){
            loadingSection2();
        }
    }

    private void loadingSection1() {
        progressBar.startAnimation(anim1);
        // Trigger Bluetooth setup
        SETUP_RESULT = setupBluetooth();
    }

    private void loadingSection2() {
        if (SETUP_RESULT == BLUETOOTH_SETUP_COMPLETE){ // Bluetooth successfully connected
            progressBar.startAnimation(anim2);
        }else{
            displayError(SETUP_RESULT);
        }
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                //Device Found!
                BluetoothDevice btDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = btDevice.getName();
                String deviceHardwareAddress = btDevice.getAddress();
                Log.i("BT_DEVICE_FOUND", "Device Name: " + deviceName);
                Log.i("BT_DEVICE_FOUND", "Device Address: " + deviceHardwareAddress);

                if(pairedDevices.contains(btDevice)){
                    if(!failedToConnectDevices.contains(btDevice)){
                        BluetoothWrapper.setConnectedDevice(new BTDeviceData(btDevice));
                        CommHandler commHandler = new CommHandler(
                                BluetoothWrapper.getConnectedDevice(),
                                BluetoothWrapper.getHandler());
                        BluetoothWrapper.setCommHandler(commHandler);

                    }
                }else{
                    failedToConnectDevices.add(btDevice);
                }

            }
        }
    };

    private int setupBluetooth() {
        BluetoothDevice connectedDevice;
        BTDeviceData connectedDeviceData;
        CommHandler commHandler;


        if (BluetoothWrapper.getBluetoothAdapter() == null){
            // Device does not support Bluetooth
            return BLUETOOTH_NOT_SUPPORTED;
        }
        if (!BluetoothWrapper.getBluetoothAdapter().isEnabled()){
            // Bluetooth is not enabled on device
            return BLUETOOTH_NOT_ENABLED;
        }



        if (!(pairedDevices.size() > 0)){
            return BLUETOOTH_NO_PAIRED_DEVICES;
        }

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        BluetoothWrapper.getBluetoothAdapter().startDiscovery();
        registerReceiver(receiver, filter);

        return BLUETOOTH_SETUP_COMPLETE;
    }


    private void displayError(int statusCode) {
        String errorMessage;
        if (statusCode == BLUETOOTH_NOT_INITIALIZED){
            errorMessage = "Error: Bluetooth not initialized";
        }else if (statusCode == BLUETOOTH_NOT_SUPPORTED){
            errorMessage = "Error: Bluetooth not suported on this device";
        }else if (statusCode == BLUETOOTH_NO_PAIRED_DEVICES){
            errorMessage = "Error: There is nothing paired to this device";
        }else{
            errorMessage = "Error: An unrecognized error has occurred";
        }
        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
    }


    public class ProgressBarAnimation extends Animation {
        private ProgressBar progressBar;
        private float from;
        private float  to;

        public ProgressBarAnimation(ProgressBar progressBar, float from, float to) {
            super();
            this.progressBar = progressBar;
            this.from = from;
            this.to = to;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            float value = from + (to - from) * interpolatedTime;
            progressBar.setProgress((int) value);
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(receiver);
        BluetoothWrapper.getBluetoothAdapter().cancelDiscovery();
    }

}
