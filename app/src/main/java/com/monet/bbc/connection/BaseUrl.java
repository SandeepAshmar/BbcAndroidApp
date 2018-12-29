package com.monet.bbc.connection;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.monet.bbc.utils.AppConstant.IS_STAGING;
import static com.monet.bbc.utils.AppConstant.S_BASE_URL;

public class BaseUrl {

    public static final String BASE_URL = IS_STAGING ? S_BASE_URL:null;
    public static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
