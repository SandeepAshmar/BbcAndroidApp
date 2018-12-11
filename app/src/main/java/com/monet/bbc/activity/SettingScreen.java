package com.monet.bbc.activity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.monet.bbc.R;

import java.util.Locale;



public class SettingScreen extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private boolean switchState = false;
    private Switch notificationStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_screen);

        toolbar = findViewById(R.id.setting_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        notificationStatus = findViewById(R.id.switch_notification);

        findViewById(R.id.tv_account).setOnClickListener(this);
        findViewById(R.id.tv_password_reset).setOnClickListener(this);
        findViewById(R.id.tv_share_and_earn).setOnClickListener(this);
        findViewById(R.id.tv_terms_and_conditions).setOnClickListener(this);
        findViewById(R.id.tv_help).setOnClickListener(this);
        notificationStatus.setOnClickListener(this);



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_account:
                toast("Account");
                break;
            case R.id.tv_password_reset:
                startActivity(new Intent(this,ResetPasswordScreen.class));
                break;
            case R.id.switch_notification:
                saveNotificationStatus();
                break;
            case R.id.tv_share_and_earn:
                toast("Share and Earn");
                break;
            case R.id.tv_terms_and_conditions:
                toast("Terms and Conditions");
                break;
            case R.id.tv_help:
                toast("Help");
                break;


        }
    }

    private void saveNotificationStatus() {
        if (notificationStatus.isChecked()) {
            toast("Switch On");
        } else {
            toast("Switch Off");
        }
    }

    private void toast(String message) {
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }
}
