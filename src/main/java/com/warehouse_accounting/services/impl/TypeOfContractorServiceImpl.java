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
        try {
            Call<TypeOfContractorDto> call = retrofit.getById(id);
            Response<TypeOfContractorDto> response = call.execute();
            return  response.body();
        }catch (Exception e){
            System.out.println(String.format("Ошибка при получении %s", id) + e);
            throw new NotFoundException(e.toString());
        }
    }

    @Override
    public void create(TypeOfContractorDto tcDTO) {
        try {
            Call<?> create = retrofit.create(tcDTO);
            Response<?> response = create.execute();
        }catch (Exception e){
            System.out.println(String.format("Ошибка при создании %s", tcDTO.toString()) + e);
            throw new NotFoundException(e.toString());
        }
    }

    @Override
    public void deleteByID(Long id) {
        try {
            Call<?> delete = retrofit.delete(id);
            Response<?> response = delete.execute();
        }catch (Exception e){
            System.out.println(String.format("Ошибка при получении %s", id) + e);
            throw new NotFoundException(e.toString());
        }
    }

    @Override
    public void update(TypeOfContractorDto tcDTO) {
        try {
            Call<?> update= retrofit.update(tcDTO);
            Response<?> response = update.execute();
        }catch (Exception e){
            System.out.println(String.format("Ошибка при обновлении %s", tcDTO) + e);
            throw new NotFoundException(e.toString());
        }
    }
}
