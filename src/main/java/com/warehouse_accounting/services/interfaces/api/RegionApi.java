package com.warehouse_accounting.services.interfaces.api;

import com.warehouse_accounting.models.dto.RegionDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

import java.util.List;

public interface RegionApi {

    @Headers("Accept: application/json")
    @GET("{url}")
    Call<List<RegionDto>> getAll(@Path(value = "url", encoded = true) String url);

    @Headers("Accept: application/json")
    @GET("{url}/{id}")
    Call<RegionDto> getById(@Path(value = "url", encoded = true) String url, @Path("id") Long id);

    @Headers("Accept: application/json")
    @GET("{url}/code/{code}")
    Call<RegionDto> getByCode(@Path(value = "url", encoded = true) String url, @Path("code") String code);
}
