package com.warehouse_accounting.services.interfaces.retrofit;

import com.warehouse_accounting.models.dto.WarehouseDto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface WarehouseApi {
    @Headers("Accept: application/json")
    @GET("{url}")
    Call<List<WarehouseDto>> getAll(@Path(value = "url", encoded = true) String url);

    @Headers("Accept: application/json")
    @GET("{url}/{id}")
    Call<WarehouseDto> getById(@Path(value = "url", encoded = true) String url, @Path("id") Long id);

    @Headers("Accept: application/json")
    @POST("{url}")
    Call<ResponseBody> create(@Path(value = "url", encoded = true) String url, @Body WarehouseDto dto);


    @Headers("Accept: application/json")
    @PUT("{url}")
    Call<ResponseBody> update(@Path(value = "url", encoded = true) String url, @Body WarehouseDto dto);

    @Headers("Accept: application/json")
    @DELETE("{url}/{id}")
    Call<ResponseBody> deleteById(@Path(value = "url", encoded = true) String url, @Path("id") Long id);
}
