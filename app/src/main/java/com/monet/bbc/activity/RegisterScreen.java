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

public class RegisterScreen extends AppCompatActivity implements View.OnClickListener {

    private EditText edt_register_email, edt_register_password, edt_register_name;
    private String email, pass, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        edt_register_email = findViewById(R.id.edt_register_email);
        edt_register_password = findViewById(R.id.edt_register_password);
        edt_register_name = findViewById(R.id.edt_register_name);

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
        email = edt_register_email.getText().toString();
        pass = edt_register_password.getText().toString();
        name = edt_register_name.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter email id", Toast.LENGTH_SHORT).show();
        } else if (pass.isEmpty()) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        } else if (name.isEmpty()) {
            Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
        } else if (email.matches(EMAIL_PATTERN)) {
            registerUser();
        } else {
            Toast.makeText(this, "Please enter valid email id", Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("NewApi")
    private void registerUser() {
        Toast.makeText(this, "user register, api call", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginScreen.class));
        finish();
    }
}
