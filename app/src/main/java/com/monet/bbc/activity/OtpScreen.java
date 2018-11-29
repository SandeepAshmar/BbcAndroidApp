package com.monet.bbc.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.monet.bbc.R;

public class OtpScreen extends AppCompatActivity {
    EditText otpOne, otpTwo, otpThree, otpFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_screen);
        otpOne = findViewById(R.id.et_otp_one);
        otpTwo = findViewById(R.id.et_otp_two);
        otpThree = findViewById(R.id.et_otp_three);
        otpFour = findViewById(R.id.et_otp_four);

        otpOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {
                    Toast.makeText(OtpScreen.this, "Its Working...", Toast.LENGTH_SHORT).show();
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
}
