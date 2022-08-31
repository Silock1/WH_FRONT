package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.TokenDto;
import com.warehouse_accounting.services.ServiceUtils;
import com.warehouse_accounting.services.interfaces.TokenService;
import com.warehouse_accounting.services.interfaces.api.TokenApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.util.List;

@Log4j2
@Service
public class TokenServiceImpl implements TokenService {

    private final TokenApi api;
    private final String url;

    public TokenServiceImpl(@Value("api/token") String url, Retrofit retrofit) {
        this.url = url;
        this.api = retrofit.create(TokenApi.class);
    }

    @Override
    public List<TokenDto> getAll() {
        Call<List<TokenDto>> call = api.getAll(url);
        return new ServiceUtils<>(TokenDto.class).getAll(call);
    }

    @Override
    public TokenDto getById(Long id) {
        Call<TokenDto> call = api.getById(url, id);
        return new ServiceUtils<>(TokenDto.class).getById(call, id);
    }

    @Override
    public void create(TokenDto dto) {
        Call<Void> call = api.create(url, dto);
        new ServiceUtils<>(TokenDto.class).create(call);
    }

    @Override
    public void update(TokenDto dto) {
        Call<Void> call = api.update(url, dto);
        new ServiceUtils<>(TokenDto.class).update(call);
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = api.deleteById(url, id);
        new ServiceUtils<>(TokenDto.class).delete(call);
    }
}

