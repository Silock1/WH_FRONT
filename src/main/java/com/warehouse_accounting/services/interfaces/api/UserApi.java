package com.warehouse_accounting.services.interfaces.api;

import com.warehouse_accounting.models.dto.EmployeeDto;
import com.warehouse_accounting.models.dto.UserDto;
import retrofit2.Call;
import retrofit2.http.*;

public interface UserApi {
    @Headers("Accept: application/json")
    @POST("{url}")
    Call<Void> getAuthUser(@Path(value = "url", encoded = true) String url, @Body UserDto userDto);

    @Headers("Accept: application/json")
    @GET("{url}/{name}")
    Call<EmployeeDto> getByUsername(@Path(value = "url", encoded = true) String url, @Path("name") String name);
}
