package com.warehouse_accounting.services.interfaces.api;

import com.warehouse_accounting.models.dto.BuildingDto;
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
    @GET("{url}/{id}")
    Call<BuildingDto> getById(@Path(value = "url", encoded = true) String url, @Path("id") Long id);
}
