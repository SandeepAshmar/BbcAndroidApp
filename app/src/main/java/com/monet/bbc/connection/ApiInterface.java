package com.monet.bbc.connection;

import com.monet.bbc.model.LoginPojo;
import com.monet.bbc.model.LoginPost;
import com.monet.bbc.model.SignUpPost;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    //LOGIN USER
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

}
