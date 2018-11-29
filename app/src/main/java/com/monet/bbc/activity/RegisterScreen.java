package com.monet.bbc.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.monet.bbc.R;

public class RegisterScreen extends AppCompatActivity implements View.OnClickListener{

    private EditText edt_register_email, edt_register_password, edt_register_confirm_password;
    private String email, pass, conPass;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        edt_register_email = findViewById(R.id.edt_register_email);
        edt_register_password = findViewById(R.id.edt_register_password);
        edt_register_confirm_password = findViewById(R.id.edt_register_confirm_password);

        findViewById(R.id.btn_register).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register:
                validate();
                break;
        }
    }

    private void validate() {
            email = edt_register_email.getText().toString();
            pass = edt_register_password.getText().toString();
            conPass = edt_register_confirm_password.getText().toString();

            if(email.isEmpty()){
                Toast.makeText(this, "Please enter email id", Toast.LENGTH_SHORT).show();
            }else if(email.matches(emailPattern)){
                Toast.makeText(this, "Please enter valid email id", Toast.LENGTH_SHORT).show();
            }else if(pass.isEmpty()){
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            }else if(conPass.isEmpty()){
                Toast.makeText(this, "Please confirm your password", Toast.LENGTH_SHORT).show();
            }else if(!pass.equals(conPass)){
                Toast.makeText(this, "Password and Confirm password should be same", Toast.LENGTH_SHORT).show();
            }else {
                registerUser();
            }

    }

    private void registerUser() {
        Toast.makeText(this, "api call", Toast.LENGTH_SHORT).show();
    }
}
