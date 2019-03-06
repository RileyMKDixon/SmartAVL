package ece492.smartavl.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;

import ece492.smartavl.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        progressBar = findViewById(R.id.SplashActivity_progressBar);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        animateProgressBar(2000);
    }

    private void animateProgressBar(int milliseconds) {
        progressBar.setMax(100);
        ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, 0, 100);
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
        anim.setAnimationListener(animationListener);
        anim.setDuration(milliseconds);
        progressBar.startAnimation(anim);
    }

    private void onProgressBarFinished() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void onProgressBarStopped(int progress) {
        // TODO: Trigger intermediate loading steps such as bluetooth searching, pairing, checking status, etc.
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
