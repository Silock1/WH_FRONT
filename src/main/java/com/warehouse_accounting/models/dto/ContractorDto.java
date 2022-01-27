package com.warehouse_accounting.models.dto;

import com.warehouse_accounting.models.dto.TypeOfContractorDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractorDto {

    private Long id;

    private String name;

    private String legalDetailInn;
    private String legalDetailKpp;
    private String contractorGroupName;
    private String sortNumber;

    private String phone;

    private String fax;

    private String email;

    private String address;

    private String commentToAddress;

    private String comment;
    private String typeOfPriceName;
    private String numberDiscountCard;
    private String legalDetailTypeOfContractorName;
    private String legalDetailAddress;

    private ContractorGroupDto contractorGroup;

//    private TypeOfContractorDto typeOfContractor;

    private TypeOfPriceDto typeOfPrice;

    private List<BankAccountDto> bankAccounts;

    private LegalDetailDto legalDetail;

}
