package com.warehouse_accounting.security;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInit {
    private final Retrofit retrofit;

    public RetrofitInit () {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);
        retrofit = new Retrofit.Builder().baseUrl("http://localhost:4446/api/v1/auth/login")
                .addConverterFactory(GsonConverterFactory.create()).client(client.build()).build();


    }
}
