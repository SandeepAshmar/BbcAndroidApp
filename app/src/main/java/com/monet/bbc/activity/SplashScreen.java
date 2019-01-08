package com.monet.bbc.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.monet.bbc.R;
import com.monet.bbc.utils.AppPreference;

import static com.monet.bbc.utils.AppUtils.checkConnection;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SplashScreenFinish splashScreenFinish = new SplashScreenFinish(1500, 1000);
        splashScreenFinish.start();

    }

    class SplashScreenFinish extends CountDownTimer {

        public SplashScreenFinish(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @SuppressLint("NewApi")
        @Override
        public void onFinish() {
            if (AppPreference.isUserLoggedOut(SplashScreen.this)) {
                startActivity(new Intent(SplashScreen.this, LoginScreen.class));
                finish();
            }else{
                startActivity(new Intent(SplashScreen.this, HomeScreen.class));
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        checkConnection(this);
        super.onResume();
    }
}
