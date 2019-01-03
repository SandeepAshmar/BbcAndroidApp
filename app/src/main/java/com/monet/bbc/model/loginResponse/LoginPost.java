package com.monet.bbc.model.loginResponse;

import com.google.gson.annotations.SerializedName;

public class LoginPost {
    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;
    @SerializedName("fullName")
    String fullName;
    @SerializedName("social_id")
    String social_id;


    //for Normal Login
    public LoginPost(String email, String password) {
        this.email = email;
        this.password = password;
    }


    //for Social Login
    public LoginPost(String email, String fullName, String social_id) {
        this.email = email;
        this.fullName = fullName;
        this.social_id = social_id;
    }

}
