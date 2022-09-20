package com.warehouse_accounting.services.interfaces.api;

import com.warehouse_accounting.models.dto.SerialNumbersDto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;

public interface SerialNumbersApi {
    @Headers("Accept: application/json")
    @GET("{url}")
    Call<List<SerialNumbersDto>> getAll(@Path(value = "url", encoded = true) String url);

    @Headers("Accept: application/json")
    @GET("{url}/{id}")
    Call<SerialNumbersDto> getById(@Path(value = "url", encoded = true) String url, @Path("sn") Long sn);

    @Headers("Accept: application/json")
    @POST("{url}")
    Call<Void> create(@Path(value = "url", encoded = true) String url, @Body SerialNumbersDto priceListDto);

    @Headers("Accept: application/json")
    @PUT("{url}")
    Call<Void> update(@Path(value = "url", encoded = true) String url, @Body SerialNumbersDto priceListDto);

    @Headers("Accept: application/json")
    @DELETE("{url}/{id}")
    Call<Void> deleteById(@Path(value = "url", encoded = true) String url, @Path("sn") Long sn);

    @GET("{url}")
    Call<ResponseBody> getExcel(@Path(value = "url", encoded = true) String url);
}
