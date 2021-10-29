package com.warehouse_accounting.models.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDto {
    private Long id;

    private Date subscriptionExpirationDate;

    private Set<TariffDto> tariff;

    private RequisitesDto requisites = new RequisitesDto();

    private EmployeeDto employee = new EmployeeDto();
}
