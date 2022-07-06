package com.warehouse_accounting.components.sales.forms.order.types;

@FunctionalInterface
public interface GridSummaryReciver {
    void allowSummary(OrderSummary summary);
}
