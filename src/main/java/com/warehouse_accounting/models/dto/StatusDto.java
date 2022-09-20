package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusDto {
    private Long id;

    private String nameOfClass; // Поле с названием класса, в котором используется статус

    private String titleOfStatus; // Название поля

    private String colorCode; // Код цвета для статуса

}
