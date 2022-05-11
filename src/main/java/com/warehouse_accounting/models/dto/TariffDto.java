package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

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

    private Date dateStartSubscription;

    private Date dateEndSubscription;

    public static TariffDto getDefaultTarifDto() {
        return new TariffDto(1L, "tarifName", 1, 1, 1,
                1, 1, true, true, true,
                60, 500, new Date(), new Date());
    }

}
