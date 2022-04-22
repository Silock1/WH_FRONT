package com.warehouse_accounting.services.interfaces.api;

import com.warehouse_accounting.models.dto.GoodsToRealizeGetDto;
import com.warehouse_accounting.models.dto.ProductionStageDto;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ProductionStageApi {

    @Headers("Accept: application/json")
    @GET("{url}")
    Call<List<ProductionStageDto>> getAll(@Path(value = "url", encoded = true) String url);

    @Headers("Accept: application/json")
    @POST("{url}")
    Call<Void> create(@Path(value = "url", encoded = true) String url, @Body ProductionStageDto productionStageDto);
}
