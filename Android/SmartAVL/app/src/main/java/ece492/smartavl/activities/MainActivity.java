package ece492.smartavl.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import ece492.smartavl.R;
import ece492.smartavl.data.VehicleData;

public class MainActivity extends MainNavigationActivity {

    private TextView vin_textView;
    private TextView status_textView;
    private TextView speed_textView;
    private TextView rpm_textView;
    private TextView runTimeSinceEngineStart_textView;
    private TextView check_engine_textView;
    private TextView latitude_textView;
    private TextView longitude_textView;

    protected void onMainSelected() {
        // do nothing
    }

    protected void onMapSelected() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
        finish();
    }

    protected void onLogSelected() {
        Intent intent = new Intent(this, LogActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        navigation.setSelectedItemId(R.id.navigation_main);

        vin_textView = findViewById(R.id.vin_textView);
        status_textView = findViewById(R.id.status_textView);
        speed_textView = findViewById(R.id.speed_textView);
        rpm_textView = findViewById(R.id.rpm_textView);
        runTimeSinceEngineStart_textView = findViewById(R.id.runTimeSinceEngineStart_textView);
        check_engine_textView = findViewById(R.id.check_engine_textView);
        latitude_textView = findViewById(R.id.latitude_textView);
        longitude_textView = findViewById(R.id.longitude_textView);

        updateDisplay();
    }

    protected void updateDisplay() {
        // update on screen elements to match data in VehicleData
        setVIN(VehicleData.getVIN());
        setConnectionStatus(VehicleData.getConnectionStatus());
        setSpeed(VehicleData.getSpeed());
        setRPM(VehicleData.getRPM());
        setRunTimeSinceVehicleStart(VehicleData.getRunTimeSinceEngineStart());
        setCheckEngineStatus(VehicleData.getCheckEngineStatus());
        setLatitude(VehicleData.getLatitude());
        setLongitude(VehicleData.getLongitude());
    }

    public void setVIN(String vin) {
        vin_textView.setText(vin);
        vin_textView.invalidate();
    }

    public boolean setConnectionStatus(int status) {
        if (status == VehicleData.STATUS_DISCONNECTED){
            status_textView.setText(getString(R.string.status_disconnected));
            status_textView.invalidate();
            return true;
        }else if (status == VehicleData.STATUS_CONNECTED){
            status_textView.setText(getString(R.string.status_connected));
            status_textView.invalidate();
            return true;
        }
        return false;
    }

    public boolean setSpeed(int speed) {
        if (speed < 0){
            return false;
        }
        speed_textView.setText(String.valueOf(speed) + " km/h");
        speed_textView.invalidate();
        return true;
    }

    public boolean setRPM(int rpm) {
        if (rpm < 0){
            return false;
        }
        rpm_textView.setText(String.valueOf(rpm) + " RPM");
        rpm_textView.invalidate();
        return true;
    }

    public boolean setRunTimeSinceVehicleStart(int runTime) {
        if (runTime < 0){
            return false;
        }
        int hours = (int) Math.floor(runTime/3600);
        int minutes = (int) Math.floor((runTime - hours * 3600)/60);
        int seconds = (int) Math.floor(runTime - hours * 3600 - minutes * 60);
        String timeString = String.format("%2d:%2d:%2d", hours, minutes, seconds);
        runTimeSinceEngineStart_textView.setText(timeString);
        runTimeSinceEngineStart_textView.invalidate();
        return true;
    }

    public boolean setCheckEngineStatus(int status) {
        if (status == VehicleData.CHECK_ENGINE_OFF){
            check_engine_textView.setText(getString(R.string.check_engine_off));
            check_engine_textView.invalidate();
            return true;
        }else if (status == VehicleData.CHECK_ENGINE_ON){
            check_engine_textView.setText(getString(R.string.check_engine_on));
            check_engine_textView.invalidate();
            return true;
        }
        return false;
    }

    public boolean setLatitude(double latitude) {
        latitude_textView.setText(String.valueOf(latitude));
        latitude_textView.invalidate();
        return true;
    }

    public boolean setLongitude(double longitude) {
        longitude_textView.setText(String.valueOf(longitude));
        longitude_textView.invalidate();
        return true;
    }

}
