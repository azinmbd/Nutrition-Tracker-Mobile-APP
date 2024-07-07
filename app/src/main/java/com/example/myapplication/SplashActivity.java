package com.example.myapplication;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startBackgroundAnimation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, SPLASH_DELAY);
    }

    private void startBackgroundAnimation() {
        ImageView backgroundImageView = findViewById(R.id.bacground);

        int screenHeight = getResources().getDisplayMetrics().heightPixels;

        ObjectAnimator backgroundAnimator = ObjectAnimator.ofFloat(backgroundImageView, "translationY", 0, screenHeight);

        backgroundAnimator.setDuration(3000);

        backgroundAnimator.setInterpolator(new LinearInterpolator());

        backgroundAnimator.setRepeatMode(ObjectAnimator.REVERSE);

        backgroundAnimator.setRepeatCount(ObjectAnimator.INFINITE);

        backgroundAnimator.start();
    }

}
