package com.warehouse_accounting;

import com.google.gson.GsonBuilder;
import com.warehouse_accounting.components.util.DateTimeDeserealizerAdapter;
import com.warehouse_accounting.components.util.LocalDateSerializerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.time.LocalDate;

@Configuration
public class AppConfig {
    @Bean
    public Retrofit retrofit(@Value("${retrofit.baseUrl}") String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
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
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder().serializeNulls().create()))
                .build();
    }
}
