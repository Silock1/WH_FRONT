package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.model.dto.TypeOfContractorDto;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface RetrofitTypeOfContractorService {
    @GET("api/tocs")
    Call<List<TypeOfContractorDto>> getAll();

    @GET("api/tocs{id}")
    Call<TypeOfContractorDto> getById(@Path("id") Long id);

    @DELETE("api/tocs/delete{id}")
    Call<?> delete(@Path("id") Long id);

    @PUT("api/tocs/update")
    Call<?> update(@Body TypeOfContractorDto dto);

    @PUT("api/tocs/add")
    Call<?> create(@Body TypeOfContractorDto dto);

}
