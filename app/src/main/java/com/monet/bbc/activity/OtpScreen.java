package com.monet.bbc.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.monet.bbc.R;

public class OtpScreen extends AppCompatActivity implements View.OnClickListener {
    private EditText otpOne, otpTwo, otpThree, otpFour;
    private String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_screen);
        otpOne = findViewById(R.id.et_otp_one);
        otpTwo = findViewById(R.id.et_otp_two);
        otpThree = findViewById(R.id.et_otp_three);
        otpFour = findViewById(R.id.et_otp_four);

        findViewById(R.id.btn_otp_next).setOnClickListener(this);

        otpOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {
                    otpTwo.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        otpTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {
                    otpThree.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        otpThree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {
                    otpFour.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_otp_next:
                validate();
                break;
        }
    }

    private void validate() {

        otp = otpOne.getText().toString()+""+otpTwo.getText().toString()+""+otpThree.getText().toString()+""+otpFour.getText().toString();

        if(otp.equals("1234")){
            startActivity(new Intent(this, ResetPasswordScreen.class));
        }else{
            Toast.makeText(this, "please enter 1234 in otp", Toast.LENGTH_SHORT).show();
        }

    }
}
