package com.warehouse_accounting.components;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Component
public class RetrofitServiceGenerator {
    @Value("${retrofit.baseUrl}")
    private String baseUrl;

    @Value("${retrofit.logLevel}")
    private String logLevel;

    public <S> S createService(Class<S> serviceClass) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.valueOf(logLevel));

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(serviceClass);
    }
}
