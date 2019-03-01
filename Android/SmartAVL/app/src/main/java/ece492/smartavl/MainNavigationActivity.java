package ece492.smartavl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public abstract class MainNavigationActivity extends AppCompatActivity {

    protected BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_main:
                    onMainSelected();
                    return true;
                case R.id.navigation_map:
                    onMapSelected();
                    return true;
                case R.id.navigation_log:
                    onLogSelected();
                    return true;
            }
            return false;
        }
    };

    protected abstract void onMainSelected();

    protected abstract void onMapSelected();

    protected abstract void onLogSelected();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
