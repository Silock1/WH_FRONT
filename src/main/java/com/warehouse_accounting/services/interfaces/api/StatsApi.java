package com.warehouse_accounting.services.interfaces.api;

import com.warehouse_accounting.models.dto.SalesChannelDto;
import com.warehouse_accounting.models.dto.StatsDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;


public interface StatsApi {
    @Headers("Accept: application/json")
    @GET("{url}")
    Call<StatsDto> getStats(@Path(value = "url", encoded = true) String url);
}
