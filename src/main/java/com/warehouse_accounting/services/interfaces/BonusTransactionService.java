package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.BonusTransactionDto;

import java.util.List;

public interface BonusTransactionService {

    List<BonusTransactionDto> getAll();

    BonusTransactionDto getById(Long id);

    void create(BonusTransactionDto bonusTransactionDto);

    void update(BonusTransactionDto bonusTransactionDto);

    void deleteById(Long id);

    List<BonusTransactionDto> filter(String filterText);

}
