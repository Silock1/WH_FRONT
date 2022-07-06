package com.warehouse_accounting.services.interfaces.api;

import com.warehouse_accounting.models.dto.CityDto;
import com.warehouse_accounting.models.dto.StreetDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface StreetApi {

    @Headers("Accept: application/json")
    @GET("{url}")
    Call<List<StreetDto>> getAll(
            @Path(value = "url", encoded = true) String url,
            @Query("regionCityCode") String regionCityCode
    );

    @Headers("Accept: application/json")
    @GET("{url}/slice")
    Call<List<StreetDto>> getSlice(
            @Path(value = "url", encoded = true) String url,
            @Query("offset") int offset,
            @Query("limit") int limit,
            @Query("name") String name,
            @Query("regionCityCode") String regionCityCode
    );

    @Headers("Accept: application/json")
    @GET("{url}/count")
    Call<Integer> getCount(
            @Path(value = "url", encoded = true) String url,
            @Query("name") String name,
            @Query("regionCityCode") String regionCityCode
    );

    @Headers("Accept: application/json")
    @GET("{url}/{id}")
    Call<StreetDto> getById(@Path(value = "url", encoded = true) String url, @Path("id") Long id);
}
