package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.TokenDto;

import java.util.List;

public interface TokenService {
    List<TokenDto> getAll();

    TokenDto getById(Long id);

    void create(TokenDto TokenDto);

    void update(TokenDto TokenDto);

    void deleteById(Long id);
}

