package com.warehouse_accounting.services.interfaces.api;

import com.warehouse_accounting.models.dto.InvoicesReceivedDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;

public interface InvoicesReceivedApi {

    @Headers("Accept: application/json")
    @GET("{url}")
    Call<List<InvoicesReceivedDto>> getAll(@Path(value = "url", encoded = true) String url);

    @Headers("Accept: application/json")
    @GET("{url}/{id}")
    Call<InvoicesReceivedDto> getById(@Path(value = "url", encoded = true) String url, @Path("id") Long id);

    @Headers("Accept: application/json")
    @POST("{url}")
    Call<Void> create(@Path(value = "url", encoded = true) String url, @Body InvoicesReceivedDto invoicesReceivedDto);

    @Headers("Accept: application/json")
    @PUT("{url}")
    Call<Void> update(@Path(value = "url", encoded = true) String url, @Body InvoicesReceivedDto invoicesReceivedDto);

    @Headers("Accept: application/json")
    @DELETE("{url}/{id}")
    Call<Void> delete(@Path(value = "url", encoded = true) String url, @Path("id") Long id);

}
