package com.monet.bbc.activity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.media.Image;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.monet.bbc.R;

public class OtpScreen extends AppCompatActivity implements View.OnClickListener {
    private EditText otpOne, otpTwo, otpThree, otpFour;
    private String otp;
    private DonutProgress prog_otp;
    private TextView tv_otp_time, tv_otp_try_again;
    private int progreTime = 60;
    private int progTime = 0;
    private RunTimer runTimer;
    private RelativeLayout rl_otp_try;
    private ImageView img_opt_rty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_otp_screen);
        otpOne = findViewById(R.id.et_otp_one);
        otpTwo = findViewById(R.id.et_otp_two);
        otpThree = findViewById(R.id.et_otp_three);
        otpFour = findViewById(R.id.et_otp_four);
        tv_otp_time = findViewById(R.id.tv_otp_time);
        prog_otp = findViewById(R.id.prog_otp);
        tv_otp_try_again = findViewById(R.id.tv_otp_try_again);
        rl_otp_try = findViewById(R.id.rl_otp_try);
        img_opt_rty = findViewById(R.id.img_opt_rty);

        prog_otp.setMax(progreTime);
        prog_otp.setText("");

        rl_otp_try.setOnClickListener(this);
        rl_otp_try.setClickable(false);

        otpValidation();

        runTimer = new RunTimer(60000, 1000);
        runTimer.start();
    }

    private void otpValidation() {
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

        otpFour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (count == 1) {
                    otp = otpOne.getText().toString() + "" + otpTwo.getText().toString() + "" + otpThree.getText().toString() + "" + otpFour.getText().toString();

                    if (otp.length() == 4) {
                        validate();
                    } else {
                        Toast.makeText(OtpScreen.this, "Please enter full otp", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_otp_try:
                startTimerAgain();
                break;
        }
    }

    @SuppressLint("NewApi")
    private void validate() {
        if (otp.equals("1234")) {
            runTimer.cancel();
            startActivity(new Intent(this, ResetPasswordScreen.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            finish();
        } else {
            otpOne.getText().clear();
            otpTwo.getText().clear();
            otpThree.getText().clear();
            otpFour.getText().clear();
            otpOne.requestFocus();
            Toast.makeText(this, "please enter 1234 in otp", Toast.LENGTH_SHORT).show();
        }
    }

    class RunTimer extends CountDownTimer {

        public RunTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            progreTime = progreTime - 1;
            progTime = progTime + 1;
            tv_otp_time.setText("" + progreTime);
            prog_otp.setDonut_progress("" + progTime);
        }

        @Override
        public void onFinish() {
            prog_otp.setDonut_progress("" + 60);
            img_opt_rty.setVisibility(View.VISIBLE);
            tv_otp_time.setVisibility(View.INVISIBLE);
            tv_otp_try_again.setText("Try Again");
            rl_otp_try.setClickable(true);
            runTimer.cancel();
        }
    }

    private void startTimerAgain() {
        progreTime = 60;
        progTime = 0;
        tv_otp_time.setText("" + progreTime);
        prog_otp.setDonut_progress("" + progTime);
        img_opt_rty.setVisibility(View.INVISIBLE);
        tv_otp_time.setVisibility(View.VISIBLE);
        tv_otp_try_again.setText("Sec");
        rl_otp_try.setClickable(false);
        runTimer.cancel();
        runTimer = new RunTimer(60000, 1000);
        runTimer.start();
    }
}
