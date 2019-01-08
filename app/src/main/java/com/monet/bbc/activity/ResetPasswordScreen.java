package com.monet.bbc.activity;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.monet.bbc.R;
import com.monet.bbc.utils.AppPreference;

import static com.monet.bbc.utils.AppPreference.getEmail;
import static com.monet.bbc.utils.AppUtils.checkConnection;

public class ResetPasswordScreen extends AppCompatActivity implements View.OnClickListener {

    private EditText edt_reset_password, edt_reset_confirm_password, edt_reset_current_password;
    private String pass, conPass, value, currentPass, emailId;
    private TextInputLayout til_reset_current_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_screen);

        edt_reset_password = findViewById(R.id.edt_reset_password);
        edt_reset_confirm_password = findViewById(R.id.edt_reset_confirm_password);
        edt_reset_current_password = findViewById(R.id.edt_reset_current_password);
        til_reset_current_password = findViewById(R.id.til_reset_current_password);

        findViewById(R.id.btn_reset).setOnClickListener(this);


        value = getIntent().getStringExtra("Screen");

        if (value.equals("setting")) {
            edt_reset_current_password.setVisibility(View.VISIBLE);
            til_reset_current_password.setVisibility(View.VISIBLE);
            emailId = getEmail(this);
        } else {
            edt_reset_current_password.setVisibility(View.GONE);
            til_reset_current_password.setVisibility(View.GONE);
            emailId = getIntent().getStringExtra("emailOtp");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_reset:
                validate();
        }
    }

    private void validate() {
        pass = edt_reset_password.getText().toString();
        conPass = edt_reset_confirm_password.getText().toString();
        currentPass = edt_reset_current_password.getText().toString();

        if (value.equals("setting")) {
            if (currentPass.isEmpty()) {
                Toast.makeText(this, "Please enter current password", Toast.LENGTH_SHORT).show();
            } else if (pass.isEmpty()) {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            } else if (conPass.isEmpty()) {
                Toast.makeText(this, "Please enter confirm password", Toast.LENGTH_SHORT).show();
            } else if (!pass.equals(conPass)) {
                Toast.makeText(this, "Password and Confirm password should be same", Toast.LENGTH_SHORT).show();
            } else {
                resetPassword();
            }
        } else {
            if (pass.isEmpty()) {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            } else if (conPass.isEmpty()) {
                Toast.makeText(this, "Please enter confirm password", Toast.LENGTH_SHORT).show();
            } else if (!pass.equals(conPass)) {
                Toast.makeText(this, "Password and confirm password should be same", Toast.LENGTH_SHORT).show();
            } else {
                resetPassword();
            }
        }
    }

    private void resetPassword() {

    }

    @Override
    protected void onResume() {
        checkConnection(this);
        super.onResume();
    }
}
