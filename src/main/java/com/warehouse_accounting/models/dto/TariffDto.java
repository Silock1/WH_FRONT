package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TariffDto {

    private Long id;

    private String tariffName;

    private Integer usersCount;

    private Integer dataSpace;

    private Integer salePointCount;

    private Integer onlineStoreCount;

    private Integer paidApplicationOptionCount;

    private Boolean isCRM;

    private Boolean isScripts;

    private Boolean extendedBonusProgram;

    private Integer paymentPeriod;

    private Integer totalPrice;

    private LocalDate dateStartSubscription;

    private LocalDate dateEndSubscription;

    public static TariffDto getDefaultTarifDto() {
        return new TariffDto(1L, "tarifName", 1, 1, 1,
                1, 1, true, true, true,
                60, 500, LocalDate.now(), LocalDate.now());
    }

}
