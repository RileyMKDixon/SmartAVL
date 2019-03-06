package ece492.smartavl.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import ece492.smartavl.R;

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

    public static final int STATUS_DISCONNECTED = 0x00;
    public static final int STATUS_CONNECTED = 0x01;
    public static final int IGNITION_OFF = 0x10;
    public static final int IGNITION_ON = 0x11;
    public static final int LOCK_UNLOCKED = 0x20;
    public static final int LOCK_LOCKED = 0x21;
    public static final int BATTERY_LOW = 0x30;
    public static final int BATTERY_GOOD = 0x31;
    public static final int HEADLIGHTS_OFF = 0x40;
    public static final int HEADLIGHTS_ON = 0x41;
    public static final int HIGH_BEAMS_OFF = 0x50;
    public static final int HIGH_BEAMS_ON = 0x51;
    public static final int CHECK_ENGINE_OFF = 0x60;
    public static final int CHECK_ENGINE_ON = 0x61;

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
        if (status == STATUS_DISCONNECTED){
            status_textView.setText(getString(R.string.status_disconnected));
            status_textView.invalidate();
            return true;
        }else if (status == STATUS_CONNECTED){
            status_textView.setText(getString(R.string.status_connected));
            status_textView.invalidate();
            return true;
        }
        return false;
    }

    public boolean setIgnitionStatus(int status) {
        if (status == IGNITION_OFF){
            ignition_textView.setText(getString(R.string.ignition_off));
            ignition_textView.invalidate();
            return true;
        }else if (status == IGNITION_ON){
            ignition_textView.setText(getString(R.string.ignition_on));
            ignition_textView.invalidate();
            return true;
        }
        return false;
    }

    public boolean setLockStatus(int status) {
        if (status == LOCK_UNLOCKED){
            lock_textView.setText(getString(R.string.lock_unlocked));
            ignition_textView.invalidate();
            return true;
        }else if (status == LOCK_LOCKED){
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

    public boolean setFuelPercentage(int percentage) {
        if (percentage < 0 || percentage > 100){
            return false;
        }
        fuel_textView.setText(String.valueOf(percentage) + "%");
        fuel_textView.invalidate();
        return true;
    }

    public boolean setBatteryStatus(int status) {
        if (status == BATTERY_LOW){
            battery_textView.setText(getString(R.string.battery_low));
            battery_textView.invalidate();
            return true;
        }else if (status == BATTERY_GOOD){
            battery_textView.setText(getString(R.string.battery_good));
            battery_textView.invalidate();
            return true;
        }
        return false;
    }

    public boolean setHeadlightStatus(int status) {
        if (status == HEADLIGHTS_OFF){
            headlights_textView.setText(getString(R.string.headlights_off));
            headlights_textView.invalidate();
            return true;
        }else if (status == HEADLIGHTS_ON){
            headlights_textView.setText(getString(R.string.headlights_on));
            headlights_textView.invalidate();
            return true;
        }
        return false;
    }

    public boolean setHighBeamStatus(int status) {
        if (status == HIGH_BEAMS_OFF){
            high_beams_textView.setText(getString(R.string.high_beams_off));
            high_beams_textView.invalidate();
            return true;
        }else if (status == HIGH_BEAMS_ON){
            high_beams_textView.setText(getString(R.string.high_beams_on));
            high_beams_textView.invalidate();
            return true;
        }
        return false;
    }


    public boolean setCheckEngineStatus(int status) {
        if (status == CHECK_ENGINE_OFF){
            check_engine_textView.setText(getString(R.string.check_engine_off));
            check_engine_textView.invalidate();
            return true;
        }else if (status == CHECK_ENGINE_ON){
            check_engine_textView.setText(getString(R.string.check_engine_on));
            check_engine_textView.invalidate();
            return true;
        }
        return false;
    }

}
