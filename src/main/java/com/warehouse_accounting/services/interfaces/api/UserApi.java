package com.warehouse_accounting.services.interfaces.api;

import com.warehouse_accounting.models.dto.UserDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {
    @Headers("Accept: application/json")
    @POST("{url}")
    Call<Void> getAuthUser(@Path(value = "url", encoded = true) String url, @Body UserDto userDto);
}
