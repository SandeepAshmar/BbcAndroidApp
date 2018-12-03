package com.monet.bbc.activity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.monet.bbc.R;

import static com.monet.bbc.utils.AppConstant.EMAIL_PATTERN;

public class ForgotPasswordScreen extends AppCompatActivity implements View.OnClickListener{

    private EditText edt_forgot_email;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        edt_forgot_email = findViewById(R.id.edt_forgot_email);

        findViewById(R.id.btn_forgot).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_forgot:
                validate();
                break;
        }
    }

    private void validate() {

        email = edt_forgot_email.getText().toString();

        if(email.isEmpty()){
            Toast.makeText(this, "Please enter email id", Toast.LENGTH_SHORT).show();
        }else if(email.matches(EMAIL_PATTERN)){
            sendOtp();
        }else{
            Toast.makeText(this, "Please enter valid email id", Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("NewApi")
    private void sendOtp() {
        Toast.makeText(this, "api call", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, OtpScreen.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}
