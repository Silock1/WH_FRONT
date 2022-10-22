package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.BonusTransactionDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.api.BonusTransactionApi;
import com.warehouse_accounting.services.interfaces.BonusTransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class BonusTransactionImpl implements BonusTransactionService {
    private final BonusTransactionApi api;
    private final String url;

    @Autowired
    public BonusTransactionImpl(@Value("${retrofit.restServices.bonusTransaction_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(BonusTransactionApi.class);
    }

    @Override
    public List<BonusTransactionDto> getAll() {
        Call<List<BonusTransactionDto>> call = api.getAll(url);
        return new ServiceUtils<>(BonusTransactionDto.class).getAll(call);
    }

    @Override
    public BonusTransactionDto getById(Long id) {
        Call<BonusTransactionDto> call = api.getById(url, id);
        return new ServiceUtils<>(BonusTransactionDto.class).getById(call, id);
    }

    @Override
    public void create(BonusTransactionDto bonusTransactionDto) {
        Call<Void> call = api.create(url, bonusTransactionDto);
        new ServiceUtils<>(BonusTransactionDto.class).create(call);
    }

    @Override
    public void update(BonusTransactionDto bonusTransactionDto) {
        Call<Void> call = api.update(url, bonusTransactionDto);
        new ServiceUtils<>(BonusTransactionDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(BonusTransactionDto.class).delete(call);

    }

    @Override
    public List<BonusTransactionDto> filter(String filterText) {
        List<BonusTransactionDto> list = new ArrayList<>();
        for (BonusTransactionDto dto : getAll()) {
            if (dto.getComment().contains(filterText)) {

                list.add(dto);
            } else if (filterText.contains(String.valueOf(dto.getId()))) {
                list.add(dto);
            }
        }

        return list;
    }
}
