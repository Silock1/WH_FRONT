package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttributeOfCalculationObjectDto {
    public static final String PACKING_PER_UNIT = "Штучная"; // default
    public static final String PACKING_PER_WEIGTH = "Весовая";
    public static final String PACKING_PER_LIQUID = "Разливная";
    public static final String ACCOUNTING_NONE = "Без специализированного учёта"; // default
    public static final String ACCOUNTING_ALCHOGOL = "Алкогольный товар";
    public static final String ACCOUNTING_SERIAL_NUMBER = "Учёт по серийным номерам";
    public static final String ACCOUNTING_PROTECTION_THINGS = "Средства индивидуальной защиты";

    private Long id;

    private String name;

    private String sortNumber;

    private Boolean isService = false;
}
