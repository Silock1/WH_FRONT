package com.warehouse_accounting.services.interfaces;

import com.warehouse_accounting.models.dto.SubscriptionDto;

import java.util.List;

public interface SubscriptionService {

    List<SubscriptionDto> getAll();

    SubscriptionDto getById(Long id);

    void create(SubscriptionDto subscriptionDto);

    void update(SubscriptionDto subscriptionDto);

    void deleteById(Long id);
}
