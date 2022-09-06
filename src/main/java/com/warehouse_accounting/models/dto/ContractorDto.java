package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractorDto {

    private Long id;
    private String name;
    private String status;
    private String code;
    private String outerCode;
    private String sortNumber;
    private String phone;
    private String fax;
    private String email;
    private AddressDto address;
    private String comment;
    private String numberDiscountCard;
    private ContractorGroupDto contractorGroup;
    private TypeOfPriceDto typeOfPrice;
    private List<ContractorFaceContactDto> contacts;  // Не создаются на беке
    private LegalDetailDto legalDetail;
    private List<Long> callIds;

}
