package com.warehouse_accounting.services.interfaces.api;

import com.warehouse_accounting.models.dto.BuildingDto;
import com.warehouse_accounting.models.dto.CityDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface BuildingApi {

    @Headers("Accept: application/json")
    @GET("{url}")
    Call<List<BuildingDto>> getAll(
            @Path(value = "url", encoded = true) String url,
            @Query("streetCode") String streetCode
    );

    @Headers("Accept: application/json")
    @GET("{url}/slice")
    Call<List<BuildingDto>> getSlice(
            @Path(value = "url", encoded = true) String url,
            @Query("offset") int offset,
            @Query("limit") int limit,
            @Query("name") String name,
            @Query("regionCityStreetCode") String regionCityStreetCode
    );

    @Headers("Accept: application/json")
    @GET("{url}/count")
    Call<Integer> getCount(
            @Path(value = "url", encoded = true) String url,
            @Query("name") String name,
            @Query("regionCityStreetCode") String regionCityStreetCode
    );

    @Headers("Accept: application/json")
    @GET("{url}/{id}")
    Call<BuildingDto> getById(@Path(value = "url", encoded = true) String url, @Path("id") Long id);
}
