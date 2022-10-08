package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.BonusTransactionDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.api.BonusTransactionApi;
import com.warehouse_accounting.services.interfaces.api.BonusTransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class BonusTransactionImpl implements BonusTransactionService {
    private final BonusTransactionApi api;
    private final String url;

    @Autowired
    public BonusTransactionImpl(@Value("${retrofit.restServices.bonusTransaction_url}")String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(BonusTransactionApi.class);
    }

    @Override
    public List<BonusTransactionDto> getAll() {
        Call<List<BonusTransactionDto>> bonusTransactionDtoCall = api.getAll(url);
        return new ServiceUtils<>(BonusTransactionDto.class).getAll(bonusTransactionDtoCall);
    }
}
