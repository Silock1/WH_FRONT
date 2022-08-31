package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.BankAccountDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.BankAccountService;
import com.warehouse_accounting.services.interfaces.api.BankAccountApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountApi api;
    private final String url;

    public BankAccountServiceImpl(@Value("${retrofit.restServices.bank_account_url}") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(BankAccountApi.class);
    }

    @Override
    public List<BankAccountDto> getAll() {
        Call<List<BankAccountDto>> call = api.getAll(url);
        return new ServiceUtils<>(BankAccountDto.class).getAll(call);
    }

    @Override
    public BankAccountDto getById(Long id) {
        Call<BankAccountDto> call = api.getById(url, id);
        return new ServiceUtils<>(BankAccountDto.class).getById(call, id);
    }

    @Override
    public void create(BankAccountDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(BankAccountDto.class).create(call);
    }

    @Override
    public void update(BankAccountDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(BankAccountDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(BankAccountDto.class).delete(call);
    }
}
