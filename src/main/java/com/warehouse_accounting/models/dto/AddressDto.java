package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private Long id;

    private Long countryId;

    private String postCode;

    private Long regionId;

    private Long cityId;

    private String cityName;

    private Long streetId;

    private String streetName;

    private Long buildingId;

    private String buildingName;

    private String office;

    private String fullAddress;

    private String other;

    private String comment;

    @Override
    public String toString() {
        return fullAddress;
    }
}
