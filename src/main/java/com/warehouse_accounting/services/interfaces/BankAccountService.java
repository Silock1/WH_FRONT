package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.BankAccountDto;

import java.util.List;

public interface BankAccountService {

    List<BankAccountDto> getAll();

    BankAccountDto getById(Long id);

    void create(BankAccountDto bankAccountDto);

    void update(BankAccountDto bankAccountDto);

    void deleteById(Long id);
}
