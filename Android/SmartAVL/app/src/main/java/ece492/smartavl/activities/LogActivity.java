package ece492.smartavl.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import ece492.smartavl.R;
import ece492.smartavl.data.VehicleLog;

public class LogActivity extends MainNavigationActivity {

    private TextView log_textView;

    protected void onMainSelected() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    protected void onMapSelected() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
        finish();
    }

    protected void onLogSelected() {
        // do nothing
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_log);
        super.onCreate(savedInstanceState);

        navigation.setSelectedItemId(R.id.navigation_log);

        log_textView = findViewById(R.id.log_content_textView);
    }

    protected void updateDisplay() {
        setLogContent(VehicleLog.getLogAsString());
    }

    public String getLogContent() {
        return log_textView.getText().toString();
    }

    public void setLogContent(String newContent) {
        log_textView.setText(newContent);
        log_textView.invalidate();
    }

}
