package com.monet.bbc.activity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.monet.bbc.R;
import com.monet.bbc.connection.ApiInterface;
import com.monet.bbc.connection.BaseUrl;
import com.monet.bbc.model.LoginPojo;
import com.monet.bbc.model.SignUpPost;
import com.monet.bbc.utils.AppPreference;
import com.monet.bbc.utils.AppUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.monet.bbc.utils.AppConstant.EMAIL_PATTERN;
import static com.monet.bbc.utils.AppPreference.setApiToken;
import static com.monet.bbc.utils.AppPreference.setEmail;
import static com.monet.bbc.utils.AppPreference.setId;
import static com.monet.bbc.utils.AppUtils.checkConnection;
import static com.monet.bbc.utils.AppUtils.shortToast;

public class RegisterScreen extends AppCompatActivity implements View.OnClickListener {

    private EditText edt_confirm_password, edt_register_password, edt_register_email;
    private String email, pass, confirmPass;
    private ProgressDialog pd;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        pd = new ProgressDialog(this);
        edt_confirm_password = findViewById(R.id.edt_confirm_password);
        edt_register_password = findViewById(R.id.edt_register_password);
        edt_register_email = findViewById(R.id.edt_register_email);
        apiInterface = BaseUrl.getRetrofit().create(ApiInterface.class);

        findViewById(R.id.btn_register).setOnClickListener(this);
        findViewById(R.id.tv_registerSignIn).setOnClickListener(this);
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                validate();
                break;
            case R.id.tv_registerSignIn:
                startActivity(new Intent(this, LoginScreen.class));
                finish();
                break;
        }
    }

    private void validate() {
        confirmPass = edt_confirm_password.getText().toString();
        pass = edt_register_password.getText().toString();
        email = edt_register_email.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter email id", Toast.LENGTH_SHORT).show();
        } else if (pass.isEmpty()) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        } else if (confirmPass.isEmpty()) {
            Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
        } else if (email.matches(EMAIL_PATTERN)) {
            registerUser();
        } else {
            Toast.makeText(this, "Please enter valid email id", Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("NewApi")
    private void registerUser() {
        pd.show();
        SignUpPost signUpPost = new SignUpPost(email, pass, confirmPass);
        Call<LoginPojo> pojoCall = apiInterface.signUpUser(signUpPost);

        pojoCall.enqueue(new Callback<LoginPojo>() {
            @Override
            public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response) {
                try {
                    if (response.body().getCode().equals("200")) {

                        setApiToken(RegisterScreen.this,response.body().getResponse().getApi_token());
                        setId(RegisterScreen.this,response.body().getResponse().get_id());
                        setEmail(RegisterScreen.this,email);
                        startActivity(new Intent(RegisterScreen.this, HomeScreen.class));
                        finish();
                    }
                    else{
                        shortToast(RegisterScreen.this,response.body().getMessage());
                    }
                } catch (Exception e) {
                    shortToast(RegisterScreen.this,e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<LoginPojo> call, Throwable t) {
                shortToast(RegisterScreen.this,"Oops! Something went wrong.");
            }
        });
    }

    @Override
    protected void onResume() {
        checkConnection(this);
        super.onResume();
    }
}
