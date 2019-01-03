package com.monet.bbc.model.register;

import com.google.gson.annotations.SerializedName;

public class SignUpPost {

    @SerializedName("email")
    String email;

    @SerializedName("password")
    String password;

    @SerializedName("confirmPassword")
    String confirmPassword;

    public SignUpPost(String email, String password, String confirmPassword) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
}
