package com.warehouse_accounting.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class RetrofitUtil {

    @Bean
    public Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://localhost:4446")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
}