package com.warehouse_accounting.services.impl;

import com.vaadin.flow.router.NotFoundException;
import com.warehouse_accounting.components.RetrofitCreateService;
import com.warehouse_accounting.model.dto.TypeOfContractorDto;
import com.warehouse_accounting.services.interfaces.RetrofitTypeOfContractorService;
import com.warehouse_accounting.services.interfaces.TypeOfContractorService;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import java.util.List;

@Service
public class TypeOfContractorServiceImpl implements TypeOfContractorService {

    private final RetrofitTypeOfContractorService retrofit;

    public TypeOfContractorServiceImpl(RetrofitCreateService createRetrofit) {
        this.retrofit = createRetrofit.retrofit.create(RetrofitTypeOfContractorService.class);
    }

    @Override
    public List<TypeOfContractorDto> getAll() {
        try {
            Call<List<TypeOfContractorDto>> listCall = retrofit.getAll();
            Response<List<TypeOfContractorDto>> listResponse = listCall.execute();
            return  listResponse.body();
        }catch (Exception e){
            System.out.println("Ошибка при получении" + e);
            throw new NotFoundException(e.toString());
        }
    }

    @Override
    public TypeOfContractorDto getById(Long id) {
        return null;
    }

    @Override
    public void create(TypeOfContractorDto tcDTO) {

    }

    @Override
    public void deleteByID(Long Id) {

    }

    @Override
    public void update(TypeOfContractorDto tcDTO) {

    }
}
