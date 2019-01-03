package com.monet.bbc.activity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.monet.bbc.R;
import com.monet.bbc.connection.ApiInterface;
import com.monet.bbc.connection.BaseUrl;
import com.monet.bbc.model.forgotPassword.ForgotResponse;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.monet.bbc.utils.AppUtils.checkConnection;
import static com.monet.bbc.utils.AppUtils.shortToast;

public class OtpScreen extends AppCompatActivity implements View.OnClickListener {
    private EditText otpOne, otpTwo, otpThree, otpFour;
    private String otp = "", generatedOtp = "", emailId = "";
    private DonutProgress prog_otp;
    private TextView tv_otp_time, tv_otp_try_again;
    private int progreTime = 60;
    private int progTime = 0;
    private RunTimer runTimer;
    private RelativeLayout rl_otp_try;
    private ImageView img_opt_rty;
    private int edtLengthTwo = 0, edtLengthThree = 0, edtLengthFour = 0;
    private boolean secoundHit = false;
    private boolean thirdHit = false;
    private boolean fourthHit = false;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_otp_screen);
        initView();
    }

    private void initView() {
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

        pd = new ProgressDialog(this);
        pd.setMessage("Sending OTP....");
        pd.setCancelable(false);

        generatedOtp = getIntent().getStringExtra("otp");
        emailId = getIntent().getStringExtra("email");

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
                    otp = otpOne.getText().toString() + "" + otpTwo.getText().toString() + "" + otpThree.getText().toString() + "" + otpFour.getText().toString();
                }
                if (otp.length() == 4) {
                    validate();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        otpTwo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (edtLengthTwo == 0) {
                    secoundHit = true;
                    edtLengthTwo = 1;
                } else if (secoundHit) {
                    otpOne.requestFocus();
                    secoundHit = false;
                    thirdHit = false;
                    fourthHit = false;
                }
                return false;
            }
        });

        otpTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {
                    otp = otpOne.getText().toString() + "" + otpTwo.getText().toString() + "" + otpThree.getText().toString() + "" + otpFour.getText().toString();
                    otpThree.requestFocus();
                    edtLengthTwo = 1;
                    secoundHit = false;
                } else {
                    edtLengthTwo = 0;
                }
                if (otp.length() == 4) {
                    validate();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        otpThree.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (edtLengthThree == 0) {
                    thirdHit = true;
                    edtLengthThree = 1;
                } else if (thirdHit) {
                    otpTwo.requestFocus();
                    secoundHit = false;
                    thirdHit = false;
                    fourthHit = false;
                }
                return false;
            }
        });

        otpThree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {
                    otp = otpOne.getText().toString() + "" + otpTwo.getText().toString() + "" + otpThree.getText().toString() + "" + otpFour.getText().toString();
                    otpFour.requestFocus();
                    edtLengthThree = 1;
                    thirdHit = false;
                } else {
                    edtLengthThree = 0;
                }
                if (otp.length() == 4) {
                    validate();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        otpFour.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (edtLengthFour == 0) {
                    fourthHit = true;
                    edtLengthFour = 1;
                } else if (fourthHit) {
                    otpThree.requestFocus();
                    secoundHit = false;
                    thirdHit = false;
                    fourthHit = false;
                }
                return false;
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
                    edtLengthFour = 1;
                    fourthHit = false;
                    if (otp.length() == 4) {
                        validate();
                    } else {
                        Toast.makeText(OtpScreen.this, "Please enter full otp", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    edtLengthFour = 0;
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
        if (!generatedOtp.equals("")) {
            if (otp.equals(generatedOtp)) {
                runTimer.cancel();
                Intent intent = new Intent(this, ResetPasswordScreen.class);
                intent.putExtra("Screen", "otp");
                intent.putExtra("emailOtp", emailId);
                startActivity(intent);
                finish();
            } else {
                if (!otp.equals("")) {
                    otpOne.getText().clear();
                    otpTwo.getText().clear();
                    otpThree.getText().clear();
                    otpFour.getText().clear();
                    otpOne.requestFocus();
                    otp = "";
                    Toast.makeText(this, "please enter valid in otp", Toast.LENGTH_SHORT).show();
                }
            }
            edtLengthTwo = 1;
            edtLengthThree = 1;
            edtLengthFour = 1;
            secoundHit = false;
            thirdHit = false;
            fourthHit = false;
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
            otp = "";
            generatedOtp = "";
            prog_otp.setDonut_progress("" + 60);
            img_opt_rty.setVisibility(View.VISIBLE);
            tv_otp_time.setVisibility(View.INVISIBLE);
            tv_otp_try_again.setText("Try Again");
            rl_otp_try.setClickable(true);
            runTimer.cancel();
        }
    }

    private void retryOtp() {
        pd.show();
        ApiInterface apiInterface = BaseUrl.getRetrofit().create(ApiInterface.class);
        Call<ForgotResponse> responseCall = apiInterface.forgotPassword(emailId);
        responseCall.enqueue(new Callback<ForgotResponse>() {
            @Override
            public void onResponse(Call<ForgotResponse> call, final Response<ForgotResponse> response) {
                pd.dismiss();
                if(response.body() == null){
                    shortToast(OtpScreen.this, response.raw().message());
                }else{
                    if(response.body().getCode().equals("200")){
                        AlertDialog.Builder builder = new AlertDialog
                                .Builder(OtpScreen.this, R.style.DialogTheme);
                        builder.setMessage("OTP has been sent on your email id, Please check your email id.");
                        builder.setCancelable(false);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                generatedOtp = response.body().getResponse();
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }else{
                        shortToast(OtpScreen.this, response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ForgotResponse> call, Throwable t) {
                pd.dismiss();
                shortToast(OtpScreen.this, "Oops! something went wrong");
            }
        });
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
        retryOtp();
    }

    @Override
    protected void onResume() {
        checkConnection(this);
        super.onResume();
    }
}
