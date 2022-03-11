package com.warehouse_accounting.services.interfaces.api;

import com.warehouse_accounting.models.dto.dadata.Example2 ;
import com.warehouse_accounting.models.dto.dadata.Query;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DadataApi {

    @Headers({"Content-Type: application/json",
            "Accept: application/json"})
    @POST("{url}")
    Call<Example2> getExample(@Path(value = "url", encoded = true) String url,
                              @Body Query inn,
                              @Header("Authorization") String authorization);
}
