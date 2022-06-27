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

    private CountryDto country;

    private String postCode;

    private RegionDto region;

    private CityDto city;

    private StreetDto street;

    private String building;

    private String office;

    private String fullAddress;

    private String other;
}
