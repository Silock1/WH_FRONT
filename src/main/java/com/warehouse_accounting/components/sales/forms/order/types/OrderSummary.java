package com.warehouse_accounting.components.sales.forms.order.types;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderSummary {
    private BigDecimal weight = BigDecimal.ZERO;
    private BigDecimal volume = BigDecimal.ZERO;
    private int amount;
    private BigDecimal priceWithoutNds = BigDecimal.ZERO;
    private BigDecimal priceWithNds = BigDecimal.ZERO;
}
