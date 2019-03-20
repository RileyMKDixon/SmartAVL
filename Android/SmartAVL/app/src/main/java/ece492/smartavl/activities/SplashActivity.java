package ece492.smartavl.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.Toast;

import ece492.smartavl.R;
import ece492.smartavl.bluetooth.BluetoothWrapper;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ProgressBarAnimation anim0;
    private ProgressBarAnimation anim1;
    private ProgressBarAnimation anim2;
    private ProgressBarAnimation anim3;
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
        anim0 = new ProgressBarAnimation(progressBar, 0, 25);
        anim1 = new ProgressBarAnimation(progressBar, 25, 50);
        anim2 = new ProgressBarAnimation(progressBar, 50, 75);
        anim3 = new ProgressBarAnimation(progressBar, 75, 100);

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
        anim3.setAnimationListener(animationListener);
        anim3.setDuration(segmentDuration);
        progressBar.startAnimation(anim0);
    }

    private void onProgressBarFinished() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void onProgressBarStopped(int progress) {
        // TODO: Trigger intermediate loading steps such as bluetooth searching, pairing, checking status, etc.
        if (progress == 25){
            loadingSection1();
        }else if (progress == 50){
            loadingSection2();
        }else if (progress == 75){
            loadingSection3();
        }
    }

    private void loadingSection1() {
        progressBar.startAnimation(anim1);
        // Trigger Bluetooth setup
        SETUP_RESULT = BluetoothWrapper.attemptSetup(getApplicationContext());
    }

    private void loadingSection2() {
        if (SETUP_RESULT == 0){ // Bluetooth successfully connected
            progressBar.startAnimation(anim2);
            // TODO: Add purpose to next section?
        }else{
            Toast.makeText(getApplicationContext(),"Failed to set up Bluetooth",Toast.LENGTH_LONG).show(); // TODO: Improve error messages
        }
    }

    private void loadingSection3() {
        progressBar.startAnimation(anim3);
        // TODO: Add purpose to next section?
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

}
