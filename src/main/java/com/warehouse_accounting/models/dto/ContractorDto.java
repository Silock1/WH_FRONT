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
    private String status;
    private String code;
    private String outerCode;
    private String contractorGroupName;
    private String sortNumber;
    private String phone;
    private String fax;
    private String email;
    private String address;
    private String commentToAddress;
    private String comment;
    private String typeOfPriceName;
    private Long contractorGroupId;
    private Long typeOfPriceId;
    private String numberDiscountCard;
    private ContractorGroupDto contractorGroup;
    private TypeOfPriceDto typeOfPrice;

    // Поля для случая getAll()
    private String legalDetailInn;
    private String legalDetailKpp;
    private String legalDetailTypeOfContractorName;
    private String legalDetailAddress; // Сюда нужен Юр.Адресс

    private List<ContractorFaceContactDto> contacts;  // Не создаются на беке
    private List<BankAccountDto> bankAccountDtos;  // Не создаются на беке
    private LegalDetailDto legalDetailDto;

    public String getName() {
        return name;
    }
}
