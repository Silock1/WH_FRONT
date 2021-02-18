package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.BankAccountDto;
import com.warehouse_accounting.services.interfaces.BankAccountService;
import com.warehouse_accounting.services.interfaces.api.BankAccountApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Log4j2
@Service
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountApi bankAccountApi;
    private final String bankAccountUrl;

    public BankAccountServiceImpl(@Value("${retrofit.restServices.bankAccount_url}") String bankAccountUrl, Retrofit retrofit) {
        this.bankAccountUrl = bankAccountUrl;
        this.bankAccountApi = retrofit.create(BankAccountApi.class);
    }

    @Override
    public List<BankAccountDto> getAll() {
        List<BankAccountDto> bankAccountDtoList = Collections.emptyList();
        Call<List<BankAccountDto>> bankAccountGetAllCall = bankAccountApi.getAll(bankAccountUrl);
        try {
            bankAccountDtoList = bankAccountGetAllCall.execute().body();
            log.info("Успешно выполнен запрос на получение списка BankAccountDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка BankAccountDto");
        }
        return bankAccountDtoList;
    }

    @Override
    public BankAccountDto getById(Long id) {
        BankAccountDto bankAccountDto = null;
        Call<BankAccountDto> callSync = bankAccountApi.getById(bankAccountUrl, id);
        try {
            Response<BankAccountDto> response = callSync.execute();
            bankAccountDto = response.body();
            log.info("Успешно выполнен запрос на получение BankAccountDto по id: {}", id);
        } catch (Exception ex) {
            log.error("Произошла ошибка при выполнении запроса на получение BankAccountDto по id: {}", id);
        }
        return bankAccountDto;
    }

    @Override
    public void create(BankAccountDto dto) {
        Call<Void> call = bankAccountApi.create(bankAccountUrl, dto);
        try {
            call.execute();
            log.info("Успешно выполнен запрос на создание BankAccountDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании BankAccountDto");
        }
    }

    @Override
    public void update(BankAccountDto dto) {
        Call<Void> call = bankAccountApi.update(bankAccountUrl, dto);
        try {
            call.execute();
            log.info("Успешно выполнен запрос на изменении BankAccountDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменении BankAccountDto");
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = bankAccountApi.deleteById(bankAccountUrl, id);
        try {
            call.execute();
            log.info("Успешно выполнен запрос на удаление BankAccountDto");
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление BankAccountDto по id: {}", id);
        }
    }
}
