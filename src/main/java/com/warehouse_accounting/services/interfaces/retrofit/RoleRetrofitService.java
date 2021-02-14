package com.warehouse_accounting.services.interfaces.retrofit;

import com.warehouse_accounting.models.dto.RoleDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;

public interface RoleRetrofitService {

    @GET("/api/role")
    Call<List<RoleDto>> getAll();

    @GET("/api/role/{id}")
    Call<RoleDto> getById(@Path("id") Long id);

    @POST("/api/role")
    Call<Void> create(@Body RoleDto dto);

    @PUT("/api/role")
    Call<Void> update(@Body RoleDto dto);

    @DELETE("/api/role/{id}")
    Call<Void> deleteById(@Path("id") Long id);
}
