package com.warehouse_accounting.services.interfaces.api;

import com.warehouse_accounting.models.dto.TypeOfContractorDto;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface RetrofitTypeOfContractorApi {

    @Headers("Accept: application/json")
    @GET("{url}")
    Call<List<TypeOfContractorDto>> getAll(@Path(value = "url", encoded = true) String url);

    @Headers("Accept: application/json")
    @GET("{url}/{id}")
    Call<TypeOfContractorDto> getById(@Path(value = "url", encoded = true) String url, @Path("id") Long id);

    @Headers("Accept: application/json")
    @DELETE("{url}/{id}")
    Call<Void> delete(@Path(value = "url", encoded = true) String url, @Path("id") Long id);

    @Headers("Accept: application/json")
    @PUT("{url}")
    Call<Void> update(@Path(value = "url", encoded = true) String url, @Body TypeOfContractorDto dto);

    @Headers("Accept: application/json")
    @PUT("{url}")
    Call<Void> create(@Path(value = "url", encoded = true) String url, @Body TypeOfContractorDto dto);

}
