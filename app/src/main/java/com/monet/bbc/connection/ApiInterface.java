package com.monet.bbc.connection;

import com.monet.bbc.model.forgotPassword.ForgotResponse;
import com.monet.bbc.model.loginResponse.LoginPojo;
import com.monet.bbc.model.loginResponse.LoginPost;
import com.monet.bbc.model.register.SignUpPost;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    /*login user*/
    @Headers("Content-Type: application/json")
    @POST("user")
    Call<LoginPojo> loginUser(@Body LoginPost loginPost);

    /*social login*/
    @Headers("Content-Type: application/json")
    @POST("social")
    Call<LoginPojo> socialLoginUser(@Body LoginPost loginPost);

    /*sign up*/
    @Headers("Content-Type: application/json")
    @POST("signup")
    Call<LoginPojo> signUpUser(@Body SignUpPost signUpPost);

    /*check email*/
    @Headers("Content-Type: application/json")
    @GET("user/create")
    Call<LoginPojo> checkEmail(@Query("email") String email);

    /*send otp*/
    @Headers("Content-Type: application/json")
    @POST("forgotPassword")
    Call<ForgotResponse> forgotPassword(@Query("email") String email);

}
