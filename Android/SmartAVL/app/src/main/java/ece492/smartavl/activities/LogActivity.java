package ece492.smartavl.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.time.LocalDateTime;

import ece492.smartavl.R;

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

    private void appendLogContent(String additionalContent) {
        setLogContent(log_textView.getText() + "\n" + additionalContent);
    }

    public void log(String str) {
        LocalDateTime date = LocalDateTime.now();
        int hour = date.getHour();
        int minute = date.getMinute();
        int second = date.getSecond();
        String timestamp = String.format("[%02d:%02d:%02d]", hour, minute, second);
        String logString = timestamp + " " + str;
        appendLogContent(logString);
    }

    public void clearLog() {
        setLogContent("");
    }

    public String getLogContent() {
        return log_textView.getText().toString();
    }

    public void setLogContent(String newContent) {
        log_textView.setText(newContent);
        log_textView.invalidate();
    }

}
