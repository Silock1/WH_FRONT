package com.warehouse_accounting.services.interfaces.api;

import com.warehouse_accounting.models.dto.InternalOrderDto;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface InternalOrderApi {

    @Headers("Accept: application/json")
    @GET("{url}")
    Call<List<InternalOrderDto>> getAll(@Path(value = "url", encoded = true) String url);

    @Headers("Accept: application/json")
    @GET("{url}/{id}")
    Call<InternalOrderDto> getById(@Path(value = "url", encoded = true) String url, @Path("id") Long id);

    @Headers("Accept: application/json")
    @POST("{url}")
    Call<Void> create(@Path(value = "url", encoded = true) String url, @Body InternalOrderDto dto);

    @Headers("Accept: application/json")
    @PUT("{url}")
    Call<Void> update(@Path(value = "url", encoded = true) String url, @Body InternalOrderDto dto);

    @Headers("Accept: application/json")
    @DELETE("{url}/{id}")
    Call<Void> deleteById(@Path(value = "url", encoded = true) String url, @Path("id") Long id);
}
