package com.monet.bbc;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.monet.bbc.activity.LoginScreen;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SplashScreenFinish splashScreenFinish = new SplashScreenFinish(1500, 1000);
        splashScreenFinish.start();

    }

    class SplashScreenFinish extends CountDownTimer{

        public SplashScreenFinish(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @SuppressLint("NewApi")
        @Override
        public void onFinish() {
            startActivity(new Intent(SplashScreen.this, LoginScreen.class),
                    ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this).toBundle());
            finish();
        }
    }
}
