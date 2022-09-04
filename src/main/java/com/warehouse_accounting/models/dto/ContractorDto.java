package com.warehouse_accounting.models.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator= ObjectIdGenerators.UUIDGenerator.class, property="@id")
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
    private AddressDto address;
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
    private AddressDto legalDetailAddress;

    private List<ContractorFaceContactDto> contacts;  // Не создаются на беке

//    private transient List<BankAccountDto> bankAccountDtos = new ArrayList<>();  // Не создаются на беке

    private LegalDetailDto legalDetailDto;
    private List<Long> callIds;

//    public String getName() {
//        return name == null ? "Contractor with id: " + id : name;
//    }
//
//    @Override
//    public String toString() {
//        return name == null ? "Company number: " + id : name;
//    }

}
