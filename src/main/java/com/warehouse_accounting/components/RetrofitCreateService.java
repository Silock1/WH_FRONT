package com.warehouse_accounting.components;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Component
public class RetrofitCreateService {



 public Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://localhost:4446/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
