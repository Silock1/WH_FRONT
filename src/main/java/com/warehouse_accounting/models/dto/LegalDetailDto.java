package com.warehouse_accounting.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.warehouse_accounting.components.util.DateConvertor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LegalDetailDto {
    private Long id;
    private String fullName;
    private String lastName;
    private String firstName;
    private String middleName;
    private AddressDto address;
    private String inn;
    private String okpo;
    private String ogrnip;
    private String kpp;
    private String numberOfTheCertificate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateConvertor.datePattern)
    private LocalDate dateOfTheCertificate;
    private TypeOfContractorDto typeOfContractor;
}
