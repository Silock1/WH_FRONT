package com.warehouse_accounting.services.interfaces.api;

import com.warehouse_accounting.models.dto.BonusTransactionDto;

import java.util.List;

public interface BonusTransactionService {

    List<BonusTransactionDto> getAll();
}
