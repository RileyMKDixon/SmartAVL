package ece492.smartavl.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import ece492.smartavl.R;
import ece492.smartavl.data.VehicleData;

public class MainActivity extends MainNavigationActivity {

    private TextView make_textView;
    private TextView model_textView;
    private TextView year_textView;
    private TextView status_textView;
    private TextView ignition_textView;
    private TextView lock_textView;
    private TextView speed_textView;
    private TextView rpm_textView;
    private TextView fuel_textView;
    private TextView battery_textView;
    private TextView headlights_textView;
    private TextView high_beams_textView;
    private TextView check_engine_textView;

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

        make_textView = findViewById(R.id.make_textView);
        model_textView = findViewById(R.id.model_textView);
        year_textView = findViewById(R.id.year_textView);
        status_textView = findViewById(R.id.status_textView);
        ignition_textView = findViewById(R.id.ignition_textView);
        lock_textView = findViewById(R.id.lock_textView);
        speed_textView = findViewById(R.id.speed_textView);
        rpm_textView = findViewById(R.id.rpm_textView);
        fuel_textView = findViewById(R.id.fuel_textView);
        battery_textView = findViewById(R.id.battery_textView);
        headlights_textView = findViewById(R.id.headlight_textView);
        high_beams_textView = findViewById(R.id.high_beam_textView);
        check_engine_textView = findViewById(R.id.check_engine_textView);

        updateDisplay();
    }

    protected void updateDisplay() {
        // update on screen elements to match data in VehicleData
        setMake(VehicleData.getMake());
        setModel(VehicleData.getModel());
        setYear(VehicleData.getYear());
        setConnectionStatus(VehicleData.getConnectionStatus());
        setIgnitionStatus(VehicleData.getIgnitionStatus());
        setLockStatus(VehicleData.getLockStatus());
        setSpeed(VehicleData.getSpeed());
        setRPM(VehicleData.getRPM());
        setFuelVolume(VehicleData.getFuelVolume());
        setBatteryStatus(VehicleData.getBatteryStatus());
        setHeadlightStatus(VehicleData.getHeadlightStatus());
        setHighBeamStatus(VehicleData.getHighBeamStatus());
        setCheckEngineStatus(VehicleData.getCheckEngineStatus());
    }

    public void setMake(String make) {
        make_textView.setText(make);
        make_textView.invalidate();
    }

    public void setModel(String model) {
        model_textView.setText(model);
        model_textView.invalidate();
    }

    public void setYear(int year) {
        year_textView.setText(String.valueOf(year));
        year_textView.invalidate();
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

    public boolean setIgnitionStatus(int status) {
        if (status == VehicleData.IGNITION_OFF){
            ignition_textView.setText(getString(R.string.ignition_off));
            ignition_textView.invalidate();
            return true;
        }else if (status == VehicleData.IGNITION_ON){
            ignition_textView.setText(getString(R.string.ignition_on));
            ignition_textView.invalidate();
            return true;
        }
        return false;
    }

    public boolean setLockStatus(int status) {
        if (status == VehicleData.LOCK_UNLOCKED){
            lock_textView.setText(getString(R.string.lock_unlocked));
            ignition_textView.invalidate();
            return true;
        }else if (status == VehicleData.LOCK_LOCKED){
            lock_textView.setText(getString(R.string.lock_locked));
            ignition_textView.invalidate();
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

    public boolean setFuelVolume(int volume) {
        if (volume < 0){
            return false;
        }
        fuel_textView.setText(String.valueOf(volume) + " L");
        fuel_textView.invalidate();
        return true;
    }

    public boolean setBatteryStatus(int status) {
        if (status == VehicleData.BATTERY_LOW){
            battery_textView.setText(getString(R.string.battery_low));
            battery_textView.invalidate();
            return true;
        }else if (status == VehicleData.BATTERY_GOOD){
            battery_textView.setText(getString(R.string.battery_good));
            battery_textView.invalidate();
            return true;
        }
        return false;
    }

    public boolean setHeadlightStatus(int status) {
        if (status == VehicleData.HEADLIGHTS_OFF){
            headlights_textView.setText(getString(R.string.headlights_off));
            headlights_textView.invalidate();
            return true;
        }else if (status == VehicleData.HEADLIGHTS_ON){
            headlights_textView.setText(getString(R.string.headlights_on));
            headlights_textView.invalidate();
            return true;
        }
        return false;
    }

    public boolean setHighBeamStatus(int status) {
        if (status == VehicleData.HIGH_BEAMS_OFF){
            high_beams_textView.setText(getString(R.string.high_beams_off));
            high_beams_textView.invalidate();
            return true;
        }else if (status == VehicleData.HIGH_BEAMS_ON){
            high_beams_textView.setText(getString(R.string.high_beams_on));
            high_beams_textView.invalidate();
            return true;
        }
        return false;
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

}
