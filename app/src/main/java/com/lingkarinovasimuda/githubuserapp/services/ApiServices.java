package com.lingkarinovasimuda.githubuserapp.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.lingkarinovasimuda.githubuserapp.utils.Helpers.BASE_URL;

public class ApiServices {
    public static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}