package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractorFaceContactDto {
    private Long id;
    private String allNames;
    private String position;
    private String phone;
    private String email;
    private String comment;
}
