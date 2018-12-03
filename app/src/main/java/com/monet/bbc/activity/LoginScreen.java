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

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {

    private EditText edt_login_email, edt_login_password;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        edt_login_email = findViewById(R.id.edt_login_email);
        edt_login_password = findViewById(R.id.edt_login_password);

        findViewById(R.id.tv_login_forgot).setOnClickListener(this);
        findViewById(R.id.tv_Login_SignUp_Link).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_fb).setOnClickListener(this);

    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_forgot:
                startActivity(new Intent(this, ForgotPasswordScreen.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;

            case R.id.tv_Login_SignUp_Link:
                startActivity(new Intent(this, RegisterScreen.class) , ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;

            case R.id.btn_login:
                validate();
                break;

            case R.id.btn_fb:
                Toast.makeText(this, "btn clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void validate() {

        email = edt_login_email.getText().toString();
        password = edt_login_password.getText().toString();

        if (email.isEmpty() && password.isEmpty() ) {
            Toast.makeText(this, "Please enter email or password", Toast.LENGTH_SHORT).show();
        } else if (email.isEmpty() ) {
            Toast.makeText(this, "Please enter email id", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        } else if (email.matches(EMAIL_PATTERN)) {
            loginUser();
        } else {
            Toast.makeText(this, "Please enter valid email id", Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("NewApi")
    private void loginUser() {
        startActivity(new Intent(LoginScreen.this, HomeScreen.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        finish();
        Toast.makeText(this, "api will call here", Toast.LENGTH_SHORT).show();

    }
}

