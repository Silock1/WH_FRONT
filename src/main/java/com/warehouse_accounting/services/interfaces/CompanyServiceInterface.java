package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.CompanyDto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;

public interface CompanyServiceInterface {

    @GET("/api/companies/")
    Call<List<CompanyDto>> getAll();

    @GET("/api/companies/{id}")
    Call<CompanyDto> getById(@Path("id") long id);

    @POST("/api/companies/")
    Call<ResponseBody> create(@Body CompanyDto companyDto);

    @PUT("/api/companies/")
    Call<ResponseBody> update(@Body CompanyDto companyDto);

    @DELETE("/api/companies/{id}")
    Call<ResponseBody> deleteById(@Path("id") Long id);
}
