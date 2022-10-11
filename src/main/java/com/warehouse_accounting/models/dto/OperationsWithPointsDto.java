package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationsWithPointsDto {
    private Long id;
    private String typeOfOperation;
    private int bonusPoints; //
    private String status; //не понял какое поле должно быть
    private LocalDateTime dateCreate; //refactoring Local date
    private LocalDateTime dateProfit; //refactoring Local date
    private String bonusProgram; //refactor BonusProgramDTO
    private CompanyDto contrAgent; //refactoring CompanyDto
    private String commentary;
}
