package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.PaymentsDto;

import java.util.List;

public interface PaymentsService {

    List<PaymentsDto> getAll();

    PaymentsDto getById(Long id);

    void create(PaymentsDto paymentsDto);

    void update(PaymentsDto paymentsDto);

    void deleteById(Long id);

}
