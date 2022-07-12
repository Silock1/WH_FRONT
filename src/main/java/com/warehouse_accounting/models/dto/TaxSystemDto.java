package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxSystemDto {
    public final static String SYSTEM_BY_PlACE = "Совпадает с точкой"; // default
    public final static String SYSTEM_BY_GROUP = "Совпадает с группой";
    public final static String SYSTEM_OSN = "ОСН";
    public final static String SYSTEM_USN_INCOME = "УСН. Доход";
    public final static String SYSTEM_USN_INCOME_OUTCOME = "УСН. Доход-Расход";
    public final static String SYSTEM_ESHN = "ЕСХН";
    public final static String SYSTEM_ENVD = "ЕНВД";
    public final static String SYSTEM_PATENT = "Патент";
    public final static String ATTRIBUTE_ANOTHER = "Иной предмет расчёта";
    public final static String ATTRIBUTE_COMPOSITE = "Составной предмет расчёта";
    public final static String ATTRIBUTE_EXCISE = "Подакцизный товар";
    public final static String ATTRIBUTE_PRODUCT = "Товар"; // default

    private Long id;
    private String name;
    private String sortNumber;
}
