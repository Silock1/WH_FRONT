package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDto {

    private Long id;
    private Boolean active;
    private String name;
    private DiscountTypeDto discountType = new DiscountTypeDto();
    private Set<ProductDto> products;
    private Set<ContractorDto> contractors;

    private BigDecimal priceTypeProductCard;
    private BigDecimal fixedDiscount;
    private BigDecimal sumCumulative;
    private BigDecimal discountPercent;
    private BigDecimal accrualPoints;
    private Integer writeOff;
    private Integer  maxPercentPayment;
    private Integer pointsAwardedInDays;

    private Boolean SimultaneousAccrualAndWriteOff;
    private Boolean welcomePoints;
    private Integer welcomePointsAccrual;
    private Boolean firstPurchase;
    private Boolean registrationBonusProgram;
}

