package com.warehouse_accounting.services.interfaces.api;

import com.warehouse_accounting.models.dto.AddressDto;
import com.warehouse_accounting.models.dto.CityDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface CityApi {

    @Headers("Accept: application/json")
    @GET("{url}")
    Call<List<CityDto>> getAll(
            @Path(value = "url", encoded = true) String url,
            @Query("regionCode") String regionCode
    );

    @Headers("Accept: application/json")
    @GET("{url}/slice")
    Call<List<CityDto>> getSlice(
            @Path(value = "url", encoded = true) String url,
            @Query("offset") int offset,
            @Query("limit") int limit,
            @Query("name") String name,
            @Query("regionCode") String regionCode
    );

    @Headers("Accept: application/json")
    @GET("{url}/count")
    Call<Integer> getCount(
            @Path(value = "url", encoded = true) String url,
            @Query("name") String name,
            @Query("regionCode") String regionCode
    );

    @Headers("Accept: application/json")
    @GET("{url}/{id}")
    Call<CityDto> getById(@Path(value = "url", encoded = true) String url, @Path("id") Long id);

    @Headers("Accept: application/json")
    @GET("{url}/code/{code}")
    Call<CityDto> getByCode(@Path(value = "url", encoded = true) String url, @Path("code") String code);
}
