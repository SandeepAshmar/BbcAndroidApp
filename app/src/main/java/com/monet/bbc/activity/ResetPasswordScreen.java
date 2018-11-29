package com.monet.bbc.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.monet.bbc.R;

public class ResetPasswordScreen extends AppCompatActivity implements View.OnClickListener{

    private EditText edt_reset_password, edt_reset_confirm_password;
    private String pass, conPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_screen);

        edt_reset_password = findViewById(R.id.edt_reset_password);
        edt_reset_confirm_password = findViewById(R.id.edt_reset_confirm_password);

        findViewById(R.id.btn_reset).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_reset:
                validate();
        }
    }

    private void validate() {
        pass = edt_reset_password.getText().toString();
        conPass = edt_reset_confirm_password.getText().toString();

        if(pass.isEmpty()){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        }else if(conPass.isEmpty()){
            Toast.makeText(this, "Please enter confirm password", Toast.LENGTH_SHORT).show();
        }else if(!pass.equals(conPass)){
            Toast.makeText(this, "Password and Confirm password should be same", Toast.LENGTH_SHORT).show();
        }else {
            resetPassword();
        }
    }

    private void resetPassword() {
        Toast.makeText(this, "api will call", Toast.LENGTH_SHORT).show();
    }
}
