package com.monet.bbc.activity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.monet.bbc.R;
import com.monet.bbc.connection.ApiInterface;
import com.monet.bbc.connection.BaseUrl;
import com.monet.bbc.model.forgotPassword.ForgotResponse;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.monet.bbc.utils.AppConstant.EMAIL_PATTERN;
import static com.monet.bbc.utils.AppUtils.shortToast;

public class ForgotPasswordScreen extends AppCompatActivity implements View.OnClickListener{

    private EditText emailId;
    private String email;
    private String otp = "";
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailId = findViewById(R.id.edt_forgot_email);

        findViewById(R.id.btn_forgot).setOnClickListener(this);

        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("Sending otp....");

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

        email = emailId.getText().toString();

        if(email.isEmpty()){
            Toast.makeText(this, "Please enter email id", Toast.LENGTH_SHORT).show();
        }else if(email.matches(EMAIL_PATTERN)){
            findViewById(R.id.btn_forgot).setClickable(false);
            sendOtp();
        }else{
            Toast.makeText(this, "Please enter valid email id", Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("NewApi")
    private void sendOtp() {
        pd.show();
        ApiInterface apiInterface = BaseUrl.getRetrofit().create(ApiInterface.class);
        Call<ForgotResponse> responseCall = apiInterface.forgotPassword(email);
        responseCall.enqueue(new Callback<ForgotResponse>() {
            @Override
            public void onResponse(Call<ForgotResponse> call, final Response<ForgotResponse> response) {
                pd.dismiss();
                findViewById(R.id.btn_forgot).setClickable(true);
                if(response.body() == null){
                    shortToast(ForgotPasswordScreen.this, response.raw().message());
                }else{
                    if(response.body().getCode().equals("200")){
                        AlertDialog.Builder builder = new AlertDialog
                                .Builder(ForgotPasswordScreen.this, R.style.DialogTheme);
                        builder.setMessage("OTP has been sent on your email id, Please check your email id.");
                        builder.setCancelable(false);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                otp = response.body().getResponse();
                                Intent intent = new Intent(ForgotPasswordScreen.this, OtpScreen.class);
                                intent.putExtra("otp", otp);
                                intent.putExtra("email", email);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.show();
                    }else{
                        shortToast(ForgotPasswordScreen.this, response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ForgotResponse> call, Throwable t) {
                pd.dismiss();
                findViewById(R.id.btn_forgot).setClickable(true);
                shortToast(ForgotPasswordScreen.this, "Oops! something went wrong");
            }
        });

    }


}
