package com.warehouse_accounting;

import com.google.gson.*;
import com.warehouse_accounting.components.util.DateConvertor;
import com.warehouse_accounting.components.util.DateTimeDeserealizerAdapter;
import com.warehouse_accounting.components.util.LocalDateSerializerAdapter;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Configuration
public class AppConfig {
    @Bean
    public Retrofit retrofit(@Value("${retrofit.baseUrl}") String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(new OkHttpClient.Builder()
                        .readTimeout(160, TimeUnit.SECONDS)
                        .connectTimeout(160, TimeUnit.SECONDS)
                        .build()
                )
                //anyway it doesn't works :(
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder()
                                .registerTypeAdapter(LocalDate.class, new DateTimeDeserealizerAdapter())
                                .registerTypeAdapter(LocalDate.class, new LocalDateSerializerAdapter())
                                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializerAdapter())
                                .serializeNulls().create()))
                .build();
    }

    @Bean
    public Retrofit retrofit2(@Value("${retrofit2.baseUrl}") String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(new OkHttpClient.Builder()
                        .readTimeout(160, TimeUnit.SECONDS)
                        .connectTimeout(160, TimeUnit.SECONDS)
                        .build()
                )
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder().serializeNulls().create()))
                .build();
    }
}

class LocalDateTimeSerializerAdapter implements JsonDeserializer<LocalDateTime> {
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), DateConvertor.dateTimeFormatter);
    }
}
