package com.warehouse_accounting.services.intarfaces;

import com.warehouse_accounting.models.dto.TaxSystemDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;

public interface TaxSystemApi {
    @Headers("Accept: application/json")
    @GET("{url}")
    public Call<List<TaxSystemDto>> getAll(@Path(value = "url", encoded = true) String url);

    @Headers("Accept: application/json")
    @GET("{url}")
    public Call<TaxSystemDto> getById(@Path(value = "url", encoded = true) String url);

    @Headers("Accept: application/json")
    @PUT("{url}")
    public Call<TaxSystemDto> update(@Path(value = "url", encoded = true) String url, @Body TaxSystemDto taxSystemDto);

    @Headers("Accept: application/json")
    @POST("{url}")
    public Call<TaxSystemDto> create(@Path(value = "url", encoded = true) String url, @Body TaxSystemDto taxSystemDto);

    @Headers("Accept: application/json")
    @DELETE("{url}")
    public Call<TaxSystemDto> deleteById(@Path(value = "url", encoded = true) String url);
}
