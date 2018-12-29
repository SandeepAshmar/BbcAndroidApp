package com.monet.bbc.connection;

import com.monet.bbc.model.LoginPojo;
import com.monet.bbc.model.LoginPost;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    //LOGIN USER
    @Headers("Content-Type: application/json")
    @POST("user")
    Call<LoginPojo> loginUser(@Body LoginPost loginPost);

    @Headers("Content-Type: application/json")
    @POST("social")
    Call<LoginPojo> socialLoginUser(@Body LoginPost loginPost);

}
