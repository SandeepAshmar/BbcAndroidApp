package com.monet.bbc.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.monet.bbc.R;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        findViewById(R.id.tv_login_forgot).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.tv_login_forgot:
                startActivity(new Intent(this, ResetPasswordScreen.class));
                break;
        }
    }
}

