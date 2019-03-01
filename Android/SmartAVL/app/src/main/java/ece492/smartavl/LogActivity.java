package ece492.smartavl;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

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

    public void setLogContent(String logContent) {
        log_textView.setText(logContent);
        log_textView.invalidate();
    }

    private void appendLogContent(String additionalContent) {
        setLogContent(log_textView.getText() + "\n" + additionalContent);
    }
}
