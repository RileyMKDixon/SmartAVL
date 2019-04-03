package ece492.smartavl.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
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
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import ece492.smartavl.R;
import ece492.smartavl.bluetooth.BTDeviceData;
import ece492.smartavl.bluetooth.BluetoothWrapper;
import ece492.smartavl.bluetooth.CommHandler;
import ece492.smartavl.data.VehicleData;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ProgressBarAnimation anim0;
    private ProgressBarAnimation anim1;
    private ProgressBarAnimation anim2;
    private ProgressBarAnimation reverseanim;
    private ProgressBarAnimation singleanim;
    private Set<BluetoothDevice> pairedDevices;
    private ArrayList<BluetoothDevice> failedToConnectDevices;

    private Handler handler = new Handler();

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
        reverseanim = new ProgressBarAnimation(progressBar, 66, 0);
        singleanim = new ProgressBarAnimation(progressBar, 0, 100);

        BluetoothWrapper.setBluetoothAdapter(BluetoothAdapter.getDefaultAdapter());
        if (BluetoothWrapper.getBluetoothAdapter() != null){ // will be null if Bluetooth is not supported
            pairedDevices = BluetoothWrapper.getBluetoothAdapter().getBondedDevices();
            failedToConnectDevices = new ArrayList<BluetoothDevice>();
        }


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
        reverseanim.setAnimationListener(animationListener);
        reverseanim.setDuration(segmentDuration);
        singleanim.setAnimationListener(animationListener);
        singleanim.setDuration(segmentDuration * 3);
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
        }else if (progress == 0){
            startWithoutBluetooth();
        }
    }

    private void loadingSection1() {
        progressBar.startAnimation(anim1);
        // Trigger Bluetooth setup
        setupBluetooth();
    }

    private void loadingSection2() {
        final int REFRESH_DELAY = 500; // milliseconds
        Date startDate = new Date();
        final long startTimeMillis = startDate.getTime();
        // Wait for Bluetooth status flag to change, with timeout
        handler.postDelayed(new Runnable(){
            public void run(){
                boolean exit = waitOnBluetoothSetup(startTimeMillis);
                if (exit){
                    endBluetoothSetup();
                }else{
                    handler.postDelayed(this, REFRESH_DELAY);
                }
            }
        }, REFRESH_DELAY);
    }

    private void startWithoutBluetooth() {
        Toast.makeText(getApplicationContext(), "Starting without Smart AVL Connection...", Toast.LENGTH_SHORT).show();
        progressBar.startAnimation(singleanim);
    }

    private boolean waitOnBluetoothSetup(long startTimeMillis) {
        long pauseLoadingDuration = 2500; // milliseconds
        long timeoutDuration = 20000; // milliseconds
        long indefiniteLoadingStartTime = startTimeMillis + pauseLoadingDuration;
        long timeoutTime = startTimeMillis + timeoutDuration;
        if (VehicleData.getBluetoothStatus() == VehicleData.BLUETOOTH_NOT_INITIALIZED){
            Date currentDate = new Date();
            if (currentDate.getTime() > timeoutTime){
                VehicleData.setBluetoothStatus(VehicleData.BLUETOOTH_SETUP_TIMEOUT);
            }else if (currentDate.getTime() > indefiniteLoadingStartTime){
                progressBar.setIndeterminate(true);
                Toast.makeText(getApplicationContext(), "Searching for SmartAVL Device...", Toast.LENGTH_LONG).show();
            }
            return false;
        }
        progressBar.setIndeterminate(false);
        return true;
    }

    private void endBluetoothSetup() {
        if (VehicleData.getBluetoothStatus() == VehicleData.BLUETOOTH_SETUP_COMPLETE){
            progressBar.startAnimation(anim2);
        }else{
            displayBluetoothError(VehicleData.getBluetoothStatus());
            progressBar.startAnimation(reverseanim);
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
                        BluetoothWrapper.getCommHandler().start();
                        VehicleData.setBluetoothStatus(VehicleData.BLUETOOTH_SETUP_COMPLETE);
                    }
                }else{
                    failedToConnectDevices.add(btDevice);
                }
            }
        }
    };

    private void setupBluetooth() {
        // set status to not initialized
        VehicleData.setBluetoothStatus(VehicleData.BLUETOOTH_NOT_INITIALIZED);

        if (BluetoothWrapper.getBluetoothAdapter() == null){
            // Device does not support Bluetooth
            VehicleData.setBluetoothStatus(VehicleData.BLUETOOTH_NOT_SUPPORTED);
            return;
        }
        if (!BluetoothWrapper.getBluetoothAdapter().isEnabled()){
            // Bluetooth is not enabled on device
            VehicleData.setBluetoothStatus(VehicleData.BLUETOOTH_NOT_ENABLED);
            return;
        }

        if (!(pairedDevices.size() > 0)){
            // There are no paired devices
            VehicleData.setBluetoothStatus(VehicleData.BLUETOOTH_NO_PAIRED_DEVICES);
            return;
        }

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        BluetoothWrapper.getBluetoothAdapter().startDiscovery();
        registerReceiver(receiver, filter);
    }


    private void displayBluetoothError(int statusCode) {
        String errorMessage;
        if (statusCode == VehicleData.BLUETOOTH_NOT_INITIALIZED){
            errorMessage = "Error: Bluetooth not initialized";
        }else if (statusCode == VehicleData.BLUETOOTH_NOT_SUPPORTED) {
            errorMessage = "Error: Bluetooth not supported on this device";
        }else if (statusCode == VehicleData.BLUETOOTH_NOT_ENABLED){
            errorMessage = "Error: Bluetooth not enabled on this device";
        }else if (statusCode == VehicleData.BLUETOOTH_NO_PAIRED_DEVICES) {
            errorMessage = "Error: There is nothing paired to this device";
        }else if (statusCode == VehicleData.BLUETOOTH_CONNECTION_FAILED) {
            errorMessage = "Error: Failed to connect to Bluetooth device";
        }else if (statusCode == VehicleData.BLUETOOTH_CONNECTION_DISCONNECTED) {
            errorMessage = "Error: Bluetooth disconnected during setup process";
        }else if (statusCode == VehicleData.BLUETOOTH_SETUP_TIMEOUT) {
            errorMessage = "Error: Could not find Smart AVL device";
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
        try{
            unregisterReceiver(receiver);
        }catch(IllegalArgumentException e){
            // in case receiver was never successfully registered due to error in setup process
        }
        if (BluetoothWrapper.getBluetoothAdapter() != null){ // will be null if Bluetooth is not supported
            BluetoothWrapper.getBluetoothAdapter().cancelDiscovery();
        }
    }

}
