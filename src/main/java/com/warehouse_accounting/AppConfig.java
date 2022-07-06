package com.warehouse_accounting;

import com.google.gson.GsonBuilder;
import com.warehouse_accounting.components.util.DateTimeDeserealizerAdapter;
import com.warehouse_accounting.components.util.LocalDateSerializerAdapter;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Configuration
public class AppConfig {
    @Bean
    public Retrofit retrofit(@Value("${retrofit.baseUrl}") String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(new OkHttpClient.Builder()
                        .readTimeout(60, TimeUnit.SECONDS)
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .build()
                )
                //anyway it doesn't works :(
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder()
                                .registerTypeAdapter(LocalDate.class, new DateTimeDeserealizerAdapter())
                                .registerTypeAdapter(LocalDate.class, new LocalDateSerializerAdapter())
                                .serializeNulls().create()))
                .build();
    }

    @Bean
    public Retrofit retrofit2(@Value("${retrofit2.baseUrl}") String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(new OkHttpClient.Builder()
                        .readTimeout(60, TimeUnit.SECONDS)
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .build()
                )
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder().serializeNulls().create()))
                .build();
    }
}
