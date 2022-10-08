package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BonusProgramDto {

    private Long id;

    private String name;

    private int accrualRule;

    private int writeOffRule;

    private int maxPercentage;

    private int bonusDelay;

    private int award;



}