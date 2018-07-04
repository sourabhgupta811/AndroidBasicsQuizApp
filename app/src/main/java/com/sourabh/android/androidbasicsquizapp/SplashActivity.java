package com.sourabh.android.androidbasicsquizapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class SplashActivity extends AppCompatActivity {
    LottieAnimationView v;
    TextView splashName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        v = findViewById(R.id.lottie_view);
        splashName = findViewById(R.id.splashName);
    }

    private void setDefaultState() {
        splashName.setTranslationY(500);
        v.setScaleX(0);
        v.setScaleY(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setDefaultState();
    }

    public void launchMainActivity(View view) {
        splashName.animate().translationYBy(-500).setDuration(500);
        v.animate().scaleXBy(1).scaleYBy(1).setDuration(1000);
        v.playAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                v.cancelAnimation();
            }
        }, 3000);
    }
}
