package com.warehouse_accounting.models.dto;

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

    private String lastName;

    private String firstName;

    private String middleName;

    private String address;

    private String commentToAddress;

    private String inn;

    private String okpo;

    private String ogrnip;

    private String numberOfTheCertificate;

    private LocalDate dateOfTheCertificate;

    private TypeOfContractorDto typeOfContractorDto = new TypeOfContractorDto();

    public LegalDetailDto(
            Long id,
            String lastName,
            String firstName,
            String middleName,
            String address,
            String commentToAddress,
            String inn,
            String okpo,
            String ogrnip,
            String numberOfTheCertificate,
            LocalDate dateOfTheCertificate,
            Long typeOfContractorDtoId) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.address = address;
        this.commentToAddress = commentToAddress;
        this.inn = inn;
        this.okpo = okpo;
        this.ogrnip = ogrnip;
        this.numberOfTheCertificate = numberOfTheCertificate;
        this.dateOfTheCertificate = dateOfTheCertificate;
        this.typeOfContractorDto.setId(typeOfContractorDtoId);
    }
}
