package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationsWithPointsDto {
    private Long id;
    private String typeOfOperation;
    private int bonusBalls;
    private String status;
    private int dateCreate; //refactoring Local date
    private int dateProfit;
    private String bonusProgram; //refactoring Object
    private String contrAgent; //refactoring Object
    private String commentary;
}
