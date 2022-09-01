package com.warehouse_accounting.services.interfaces.api;

import com.warehouse_accounting.models.dto.TechnologicalMapDto;
import com.warehouse_accounting.models.dto.TechnologicalMapGroupDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;

public interface TechnologicalMapGroupApi {

    @Headers("Accept: application/json")
    @GET("{url}")
    Call<List<TechnologicalMapGroupDto>> getAll(@Path(value = "url", encoded = true) String url);

    @Headers("Accept: application/json")
    @GET("{url}/{id}")
    Call<TechnologicalMapGroupDto> getById(@Path(value = "url", encoded = true) String url, @Path("id") Long id);

    @Headers("Accept: application/json")
    @POST("{url}")
    Call<Void> create(@Path(value = "url", encoded = true) String url, @Body TechnologicalMapGroupDto technologicalMapGroupDto);

    @Headers("Accept: application/json")
    @PUT("{url}")
    Call<Void> update(@Path(value = "url", encoded = true) String url, @Body TechnologicalMapGroupDto technologicalMapGroupDto);

    @Headers("Accept: application/json")
    @DELETE("{url}/{id}")
    Call<Void> deleteById(@Path(value = "url", encoded = true) String url, @Path("id") Long id);
}
