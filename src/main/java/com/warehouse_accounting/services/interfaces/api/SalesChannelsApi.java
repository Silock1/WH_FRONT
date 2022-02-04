package com.warehouse_accounting.services.interfaces.api;

import com.warehouse_accounting.models.dto.SalesChannelDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;

public interface SalesChannelsApi {


    @Headers("Accept: application/json")
    @GET("{url}")
    Call<List<SalesChannelDto>> getAll(@Path(value = "url", encoded = true) String url);

    @Headers("Accept: application/json")
    @GET("{url}/{id}")
    Call<SalesChannelDto> getById(@Path(value = "url", encoded = true) String url, @Path("id") Long id);

    @Headers("Accept: application/json")
    @POST
    Call<Void> create(@Path(value = "url",encoded = true) String url, @Body SalesChannelDto dto);

    @Headers("Accept: application/json")
    @PUT
    Call<Void> update(@Path(value = "url", encoded = true) String url, @Body SalesChannelDto dto);

    @Headers("Accept: application/json")
    @DELETE
    Call<Void> deleteById(@Path(value = "url", encoded = true) String url, @Path("id") Long id);
}
