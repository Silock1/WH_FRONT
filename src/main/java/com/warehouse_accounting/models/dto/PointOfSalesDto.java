package com.warehouse_accounting.models.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointOfSalesDto {

    private Long id;
    private Boolean checkboxEnabled;
    private String name;
    private String activity; // заглушки, пока не реализованы нужные классы
    private String type; //
    private BigDecimal revenue;//
    private BigDecimal cheque;//
    private BigDecimal averageСheck;//
    private BigDecimal moneyInTheCashRegister;//
    private String cashiers; //
    private String synchronization;//
    private String FN;//
    private String validityPeriodFN;//

}
