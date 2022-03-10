
package com.warehouse_accounting.models.dto.dadata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "postal_code",
    "country",
    "country_iso_code",
    "federal_district",
    "region_fias_id",
    "region_kladr_id",
    "region_iso_code",
    "region_with_type",
    "region_type",
    "region_type_full",
    "region",
    "area_fias_id",
    "area_kladr_id",
    "area_with_type",
    "area_type",
    "area_type_full",
    "area",
    "city_fias_id",
    "city_kladr_id",
    "city_with_type",
    "city_type",
    "city_type_full",
    "city",
    "city_area",
    "city_district_fias_id",
    "city_district_kladr_id",
    "city_district_with_type",
    "city_district_type",
    "city_district_type_full",
    "city_district",
    "settlement_fias_id",
    "settlement_kladr_id",
    "settlement_with_type",
    "settlement_type",
    "settlement_type_full",
    "settlement",
    "street_fias_id",
    "street_kladr_id",
    "street_with_type",
    "street_type",
    "street_type_full",
    "street",
    "stead_fias_id",
    "stead_cadnum",
    "stead_type",
    "stead_type_full",
    "stead",
    "house_fias_id",
    "house_kladr_id",
    "house_cadnum",
    "house_type",
    "house_type_full",
    "house",
    "block_type",
    "block_type_full",
    "block",
    "entrance",
    "floor",
    "flat_fias_id",
    "flat_cadnum",
    "flat_type",
    "flat_type_full",
    "flat",
    "flat_area",
    "square_meter_price",
    "flat_price",
    "postal_box",
    "fias_id",
    "fias_code",
    "fias_level",
    "fias_actuality_state",
    "kladr_id",
    "geoname_id",
    "capital_marker",
    "okato",
    "oktmo",
    "tax_office",
    "tax_office_legal",
    "timezone",
    "geo_lat",
    "geo_lon",
    "beltway_hit",
    "beltway_distance",
    "metro",
    "qc_geo",
    "qc_complete",
    "qc_house",
    "history_values",
    "unparsed_parts",
    "source",
    "qc"
})
@Generated("jsonschema2pojo")
public class Data__1 {

    @JsonProperty("postal_code")
    private String postalCode;
    @JsonProperty("country")
    private String country;
    @JsonProperty("country_iso_code")
    private String countryIsoCode;
    @JsonProperty("federal_district")
    private String federalDistrict;
    @JsonProperty("region_fias_id")
    private String regionFiasId;
    @JsonProperty("region_kladr_id")
    private String regionKladrId;
    @JsonProperty("region_iso_code")
    private String regionIsoCode;
    @JsonProperty("region_with_type")
    private String regionWithType;
    @JsonProperty("region_type")
    private String regionType;
    @JsonProperty("region_type_full")
    private String regionTypeFull;
    @JsonProperty("region")
    private String region;
    @JsonProperty("area_fias_id")
    private Object areaFiasId;
    @JsonProperty("area_kladr_id")
    private Object areaKladrId;
    @JsonProperty("area_with_type")
    private Object areaWithType;
    @JsonProperty("area_type")
    private Object areaType;
    @JsonProperty("area_type_full")
    private Object areaTypeFull;
    @JsonProperty("area")
    private Object area;
    @JsonProperty("city_fias_id")
    private String cityFiasId;
    @JsonProperty("city_kladr_id")
    private String cityKladrId;
    @JsonProperty("city_with_type")
    private String cityWithType;
    @JsonProperty("city_type")
    private String cityType;
    @JsonProperty("city_type_full")
    private String cityTypeFull;
    @JsonProperty("city")
    private String city;
    @JsonProperty("city_area")
    private Object cityArea;
    @JsonProperty("city_district_fias_id")
    private Object cityDistrictFiasId;
    @JsonProperty("city_district_kladr_id")
    private Object cityDistrictKladrId;
    @JsonProperty("city_district_with_type")
    private Object cityDistrictWithType;
    @JsonProperty("city_district_type")
    private Object cityDistrictType;
    @JsonProperty("city_district_type_full")
    private Object cityDistrictTypeFull;
    @JsonProperty("city_district")
    private Object cityDistrict;
    @JsonProperty("settlement_fias_id")
    private Object settlementFiasId;
    @JsonProperty("settlement_kladr_id")
    private Object settlementKladrId;
    @JsonProperty("settlement_with_type")
    private Object settlementWithType;
    @JsonProperty("settlement_type")
    private Object settlementType;
    @JsonProperty("settlement_type_full")
    private Object settlementTypeFull;
    @JsonProperty("settlement")
    private Object settlement;
    @JsonProperty("street_fias_id")
    private String streetFiasId;
    @JsonProperty("street_kladr_id")
    private String streetKladrId;
    @JsonProperty("street_with_type")
    private String streetWithType;
    @JsonProperty("street_type")
    private String streetType;
    @JsonProperty("street_type_full")
    private String streetTypeFull;
    @JsonProperty("street")
    private String street;
    @JsonProperty("stead_fias_id")
    private Object steadFiasId;
    @JsonProperty("stead_cadnum")
    private Object steadCadnum;
    @JsonProperty("stead_type")
    private Object steadType;
    @JsonProperty("stead_type_full")
    private Object steadTypeFull;
    @JsonProperty("stead")
    private Object stead;
    @JsonProperty("house_fias_id")
    private String houseFiasId;
    @JsonProperty("house_kladr_id")
    private String houseKladrId;
    @JsonProperty("house_cadnum")
    private Object houseCadnum;
    @JsonProperty("house_type")
    private String houseType;
    @JsonProperty("house_type_full")
    private String houseTypeFull;
    @JsonProperty("house")
    private String house;
    @JsonProperty("block_type")
    private Object blockType;
    @JsonProperty("block_type_full")
    private Object blockTypeFull;
    @JsonProperty("block")
    private Object block;
    @JsonProperty("entrance")
    private Object entrance;
    @JsonProperty("floor")
    private Object floor;
    @JsonProperty("flat_fias_id")
    private Object flatFiasId;
    @JsonProperty("flat_cadnum")
    private Object flatCadnum;
    @JsonProperty("flat_type")
    private String flatType;
    @JsonProperty("flat_type_full")
    private String flatTypeFull;
    @JsonProperty("flat")
    private String flat;
    @JsonProperty("flat_area")
    private Object flatArea;
    @JsonProperty("square_meter_price")
    private Object squareMeterPrice;
    @JsonProperty("flat_price")
    private Object flatPrice;
    @JsonProperty("postal_box")
    private Object postalBox;
    @JsonProperty("fias_id")
    private String fiasId;
    @JsonProperty("fias_code")
    private String fiasCode;
    @JsonProperty("fias_level")
    private String fiasLevel;
    @JsonProperty("fias_actuality_state")
    private String fiasActualityState;
    @JsonProperty("kladr_id")
    private String kladrId;
    @JsonProperty("geoname_id")
    private String geonameId;
    @JsonProperty("capital_marker")
    private String capitalMarker;
    @JsonProperty("okato")
    private String okato;
    @JsonProperty("oktmo")
    private String oktmo;
    @JsonProperty("tax_office")
    private String taxOffice;
    @JsonProperty("tax_office_legal")
    private String taxOfficeLegal;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("geo_lat")
    private String geoLat;
    @JsonProperty("geo_lon")
    private String geoLon;
    @JsonProperty("beltway_hit")
    private Object beltwayHit;
    @JsonProperty("beltway_distance")
    private Object beltwayDistance;
    @JsonProperty("metro")
    private Object metro;
    @JsonProperty("qc_geo")
    private String qcGeo;
    @JsonProperty("qc_complete")
    private Object qcComplete;
    @JsonProperty("qc_house")
    private Object qcHouse;
    @JsonProperty("history_values")
    private Object historyValues;
    @JsonProperty("unparsed_parts")
    private Object unparsedParts;
    @JsonProperty("source")
    private String source;
    @JsonProperty("qc")
    private String qc;

    @JsonProperty("postal_code")
    public String getPostalCode() {
        return postalCode;
    }

    @JsonProperty("postal_code")
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("country_iso_code")
    public String getCountryIsoCode() {
        return countryIsoCode;
    }

    @JsonProperty("country_iso_code")
    public void setCountryIsoCode(String countryIsoCode) {
        this.countryIsoCode = countryIsoCode;
    }

    @JsonProperty("federal_district")
    public String getFederalDistrict() {
        return federalDistrict;
    }

    @JsonProperty("federal_district")
    public void setFederalDistrict(String federalDistrict) {
        this.federalDistrict = federalDistrict;
    }

    @JsonProperty("region_fias_id")
    public String getRegionFiasId() {
        return regionFiasId;
    }

    @JsonProperty("region_fias_id")
    public void setRegionFiasId(String regionFiasId) {
        this.regionFiasId = regionFiasId;
    }

    @JsonProperty("region_kladr_id")
    public String getRegionKladrId() {
        return regionKladrId;
    }

    @JsonProperty("region_kladr_id")
    public void setRegionKladrId(String regionKladrId) {
        this.regionKladrId = regionKladrId;
    }

    @JsonProperty("region_iso_code")
    public String getRegionIsoCode() {
        return regionIsoCode;
    }

    @JsonProperty("region_iso_code")
    public void setRegionIsoCode(String regionIsoCode) {
        this.regionIsoCode = regionIsoCode;
    }

    @JsonProperty("region_with_type")
    public String getRegionWithType() {
        return regionWithType;
    }

    @JsonProperty("region_with_type")
    public void setRegionWithType(String regionWithType) {
        this.regionWithType = regionWithType;
    }

    @JsonProperty("region_type")
    public String getRegionType() {
        return regionType;
    }

    @JsonProperty("region_type")
    public void setRegionType(String regionType) {
        this.regionType = regionType;
    }

    @JsonProperty("region_type_full")
    public String getRegionTypeFull() {
        return regionTypeFull;
    }

    @JsonProperty("region_type_full")
    public void setRegionTypeFull(String regionTypeFull) {
        this.regionTypeFull = regionTypeFull;
    }

    @JsonProperty("region")
    public String getRegion() {
        return region;
    }

    @JsonProperty("region")
    public void setRegion(String region) {
        this.region = region;
    }

    @JsonProperty("area_fias_id")
    public Object getAreaFiasId() {
        return areaFiasId;
    }

    @JsonProperty("area_fias_id")
    public void setAreaFiasId(Object areaFiasId) {
        this.areaFiasId = areaFiasId;
    }

    @JsonProperty("area_kladr_id")
    public Object getAreaKladrId() {
        return areaKladrId;
    }

    @JsonProperty("area_kladr_id")
    public void setAreaKladrId(Object areaKladrId) {
        this.areaKladrId = areaKladrId;
    }

    @JsonProperty("area_with_type")
    public Object getAreaWithType() {
        return areaWithType;
    }

    @JsonProperty("area_with_type")
    public void setAreaWithType(Object areaWithType) {
        this.areaWithType = areaWithType;
    }

    @JsonProperty("area_type")
    public Object getAreaType() {
        return areaType;
    }

    @JsonProperty("area_type")
    public void setAreaType(Object areaType) {
        this.areaType = areaType;
    }

    @JsonProperty("area_type_full")
    public Object getAreaTypeFull() {
        return areaTypeFull;
    }

    @JsonProperty("area_type_full")
    public void setAreaTypeFull(Object areaTypeFull) {
        this.areaTypeFull = areaTypeFull;
    }

    @JsonProperty("area")
    public Object getArea() {
        return area;
    }

    @JsonProperty("area")
    public void setArea(Object area) {
        this.area = area;
    }

    @JsonProperty("city_fias_id")
    public String getCityFiasId() {
        return cityFiasId;
    }

    @JsonProperty("city_fias_id")
    public void setCityFiasId(String cityFiasId) {
        this.cityFiasId = cityFiasId;
    }

    @JsonProperty("city_kladr_id")
    public String getCityKladrId() {
        return cityKladrId;
    }

    @JsonProperty("city_kladr_id")
    public void setCityKladrId(String cityKladrId) {
        this.cityKladrId = cityKladrId;
    }

    @JsonProperty("city_with_type")
    public String getCityWithType() {
        return cityWithType;
    }

    @JsonProperty("city_with_type")
    public void setCityWithType(String cityWithType) {
        this.cityWithType = cityWithType;
    }

    @JsonProperty("city_type")
    public String getCityType() {
        return cityType;
    }

    @JsonProperty("city_type")
    public void setCityType(String cityType) {
        this.cityType = cityType;
    }

    @JsonProperty("city_type_full")
    public String getCityTypeFull() {
        return cityTypeFull;
    }

    @JsonProperty("city_type_full")
    public void setCityTypeFull(String cityTypeFull) {
        this.cityTypeFull = cityTypeFull;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("city_area")
    public Object getCityArea() {
        return cityArea;
    }

    @JsonProperty("city_area")
    public void setCityArea(Object cityArea) {
        this.cityArea = cityArea;
    }

    @JsonProperty("city_district_fias_id")
    public Object getCityDistrictFiasId() {
        return cityDistrictFiasId;
    }

    @JsonProperty("city_district_fias_id")
    public void setCityDistrictFiasId(Object cityDistrictFiasId) {
        this.cityDistrictFiasId = cityDistrictFiasId;
    }

    @JsonProperty("city_district_kladr_id")
    public Object getCityDistrictKladrId() {
        return cityDistrictKladrId;
    }

    @JsonProperty("city_district_kladr_id")
    public void setCityDistrictKladrId(Object cityDistrictKladrId) {
        this.cityDistrictKladrId = cityDistrictKladrId;
    }

    @JsonProperty("city_district_with_type")
    public Object getCityDistrictWithType() {
        return cityDistrictWithType;
    }

    @JsonProperty("city_district_with_type")
    public void setCityDistrictWithType(Object cityDistrictWithType) {
        this.cityDistrictWithType = cityDistrictWithType;
    }

    @JsonProperty("city_district_type")
    public Object getCityDistrictType() {
        return cityDistrictType;
    }

    @JsonProperty("city_district_type")
    public void setCityDistrictType(Object cityDistrictType) {
        this.cityDistrictType = cityDistrictType;
    }

    @JsonProperty("city_district_type_full")
    public Object getCityDistrictTypeFull() {
        return cityDistrictTypeFull;
    }

    @JsonProperty("city_district_type_full")
    public void setCityDistrictTypeFull(Object cityDistrictTypeFull) {
        this.cityDistrictTypeFull = cityDistrictTypeFull;
    }

    @JsonProperty("city_district")
    public Object getCityDistrict() {
        return cityDistrict;
    }

    @JsonProperty("city_district")
    public void setCityDistrict(Object cityDistrict) {
        this.cityDistrict = cityDistrict;
    }

    @JsonProperty("settlement_fias_id")
    public Object getSettlementFiasId() {
        return settlementFiasId;
    }

    @JsonProperty("settlement_fias_id")
    public void setSettlementFiasId(Object settlementFiasId) {
        this.settlementFiasId = settlementFiasId;
    }

    @JsonProperty("settlement_kladr_id")
    public Object getSettlementKladrId() {
        return settlementKladrId;
    }

    @JsonProperty("settlement_kladr_id")
    public void setSettlementKladrId(Object settlementKladrId) {
        this.settlementKladrId = settlementKladrId;
    }

    @JsonProperty("settlement_with_type")
    public Object getSettlementWithType() {
        return settlementWithType;
    }

    @JsonProperty("settlement_with_type")
    public void setSettlementWithType(Object settlementWithType) {
        this.settlementWithType = settlementWithType;
    }

    @JsonProperty("settlement_type")
    public Object getSettlementType() {
        return settlementType;
    }

    @JsonProperty("settlement_type")
    public void setSettlementType(Object settlementType) {
        this.settlementType = settlementType;
    }

    @JsonProperty("settlement_type_full")
    public Object getSettlementTypeFull() {
        return settlementTypeFull;
    }

    @JsonProperty("settlement_type_full")
    public void setSettlementTypeFull(Object settlementTypeFull) {
        this.settlementTypeFull = settlementTypeFull;
    }

    @JsonProperty("settlement")
    public Object getSettlement() {
        return settlement;
    }

    @JsonProperty("settlement")
    public void setSettlement(Object settlement) {
        this.settlement = settlement;
    }

    @JsonProperty("street_fias_id")
    public String getStreetFiasId() {
        return streetFiasId;
    }

    @JsonProperty("street_fias_id")
    public void setStreetFiasId(String streetFiasId) {
        this.streetFiasId = streetFiasId;
    }

    @JsonProperty("street_kladr_id")
    public String getStreetKladrId() {
        return streetKladrId;
    }

    @JsonProperty("street_kladr_id")
    public void setStreetKladrId(String streetKladrId) {
        this.streetKladrId = streetKladrId;
    }

    @JsonProperty("street_with_type")
    public String getStreetWithType() {
        return streetWithType;
    }

    @JsonProperty("street_with_type")
    public void setStreetWithType(String streetWithType) {
        this.streetWithType = streetWithType;
    }

    @JsonProperty("street_type")
    public String getStreetType() {
        return streetType;
    }

    @JsonProperty("street_type")
    public void setStreetType(String streetType) {
        this.streetType = streetType;
    }

    @JsonProperty("street_type_full")
    public String getStreetTypeFull() {
        return streetTypeFull;
    }

    @JsonProperty("street_type_full")
    public void setStreetTypeFull(String streetTypeFull) {
        this.streetTypeFull = streetTypeFull;
    }

    @JsonProperty("street")
    public String getStreet() {
        return street;
    }

    @JsonProperty("street")
    public void setStreet(String street) {
        this.street = street;
    }

    @JsonProperty("stead_fias_id")
    public Object getSteadFiasId() {
        return steadFiasId;
    }

    @JsonProperty("stead_fias_id")
    public void setSteadFiasId(Object steadFiasId) {
        this.steadFiasId = steadFiasId;
    }

    @JsonProperty("stead_cadnum")
    public Object getSteadCadnum() {
        return steadCadnum;
    }

    @JsonProperty("stead_cadnum")
    public void setSteadCadnum(Object steadCadnum) {
        this.steadCadnum = steadCadnum;
    }

    @JsonProperty("stead_type")
    public Object getSteadType() {
        return steadType;
    }

    @JsonProperty("stead_type")
    public void setSteadType(Object steadType) {
        this.steadType = steadType;
    }

    @JsonProperty("stead_type_full")
    public Object getSteadTypeFull() {
        return steadTypeFull;
    }

    @JsonProperty("stead_type_full")
    public void setSteadTypeFull(Object steadTypeFull) {
        this.steadTypeFull = steadTypeFull;
    }

    @JsonProperty("stead")
    public Object getStead() {
        return stead;
    }

    @JsonProperty("stead")
    public void setStead(Object stead) {
        this.stead = stead;
    }

    @JsonProperty("house_fias_id")
    public String getHouseFiasId() {
        return houseFiasId;
    }

    @JsonProperty("house_fias_id")
    public void setHouseFiasId(String houseFiasId) {
        this.houseFiasId = houseFiasId;
    }

    @JsonProperty("house_kladr_id")
    public String getHouseKladrId() {
        return houseKladrId;
    }

    @JsonProperty("house_kladr_id")
    public void setHouseKladrId(String houseKladrId) {
        this.houseKladrId = houseKladrId;
    }

    @JsonProperty("house_cadnum")
    public Object getHouseCadnum() {
        return houseCadnum;
    }

    @JsonProperty("house_cadnum")
    public void setHouseCadnum(Object houseCadnum) {
        this.houseCadnum = houseCadnum;
    }

    @JsonProperty("house_type")
    public String getHouseType() {
        return houseType;
    }

    @JsonProperty("house_type")
    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    @JsonProperty("house_type_full")
    public String getHouseTypeFull() {
        return houseTypeFull;
    }

    @JsonProperty("house_type_full")
    public void setHouseTypeFull(String houseTypeFull) {
        this.houseTypeFull = houseTypeFull;
    }

    @JsonProperty("house")
    public String getHouse() {
        return house;
    }

    @JsonProperty("house")
    public void setHouse(String house) {
        this.house = house;
    }

    @JsonProperty("block_type")
    public Object getBlockType() {
        return blockType;
    }

    @JsonProperty("block_type")
    public void setBlockType(Object blockType) {
        this.blockType = blockType;
    }

    @JsonProperty("block_type_full")
    public Object getBlockTypeFull() {
        return blockTypeFull;
    }

    @JsonProperty("block_type_full")
    public void setBlockTypeFull(Object blockTypeFull) {
        this.blockTypeFull = blockTypeFull;
    }

    @JsonProperty("block")
    public Object getBlock() {
        return block;
    }

    @JsonProperty("block")
    public void setBlock(Object block) {
        this.block = block;
    }

    @JsonProperty("entrance")
    public Object getEntrance() {
        return entrance;
    }

    @JsonProperty("entrance")
    public void setEntrance(Object entrance) {
        this.entrance = entrance;
    }

    @JsonProperty("floor")
    public Object getFloor() {
        return floor;
    }

    @JsonProperty("floor")
    public void setFloor(Object floor) {
        this.floor = floor;
    }

    @JsonProperty("flat_fias_id")
    public Object getFlatFiasId() {
        return flatFiasId;
    }

    @JsonProperty("flat_fias_id")
    public void setFlatFiasId(Object flatFiasId) {
        this.flatFiasId = flatFiasId;
    }

    @JsonProperty("flat_cadnum")
    public Object getFlatCadnum() {
        return flatCadnum;
    }

    @JsonProperty("flat_cadnum")
    public void setFlatCadnum(Object flatCadnum) {
        this.flatCadnum = flatCadnum;
    }

    @JsonProperty("flat_type")
    public String getFlatType() {
        return flatType;
    }

    @JsonProperty("flat_type")
    public void setFlatType(String flatType) {
        this.flatType = flatType;
    }

    @JsonProperty("flat_type_full")
    public String getFlatTypeFull() {
        return flatTypeFull;
    }

    @JsonProperty("flat_type_full")
    public void setFlatTypeFull(String flatTypeFull) {
        this.flatTypeFull = flatTypeFull;
    }

    @JsonProperty("flat")
    public String getFlat() {
        return flat;
    }

    @JsonProperty("flat")
    public void setFlat(String flat) {
        this.flat = flat;
    }

    @JsonProperty("flat_area")
    public Object getFlatArea() {
        return flatArea;
    }

    @JsonProperty("flat_area")
    public void setFlatArea(Object flatArea) {
        this.flatArea = flatArea;
    }

    @JsonProperty("square_meter_price")
    public Object getSquareMeterPrice() {
        return squareMeterPrice;
    }

    @JsonProperty("square_meter_price")
    public void setSquareMeterPrice(Object squareMeterPrice) {
        this.squareMeterPrice = squareMeterPrice;
    }

    @JsonProperty("flat_price")
    public Object getFlatPrice() {
        return flatPrice;
    }

    @JsonProperty("flat_price")
    public void setFlatPrice(Object flatPrice) {
        this.flatPrice = flatPrice;
    }

    @JsonProperty("postal_box")
    public Object getPostalBox() {
        return postalBox;
    }

    @JsonProperty("postal_box")
    public void setPostalBox(Object postalBox) {
        this.postalBox = postalBox;
    }

    @JsonProperty("fias_id")
    public String getFiasId() {
        return fiasId;
    }

    @JsonProperty("fias_id")
    public void setFiasId(String fiasId) {
        this.fiasId = fiasId;
    }

    @JsonProperty("fias_code")
    public String getFiasCode() {
        return fiasCode;
    }

    @JsonProperty("fias_code")
    public void setFiasCode(String fiasCode) {
        this.fiasCode = fiasCode;
    }

    @JsonProperty("fias_level")
    public String getFiasLevel() {
        return fiasLevel;
    }

    @JsonProperty("fias_level")
    public void setFiasLevel(String fiasLevel) {
        this.fiasLevel = fiasLevel;
    }

    @JsonProperty("fias_actuality_state")
    public String getFiasActualityState() {
        return fiasActualityState;
    }

    @JsonProperty("fias_actuality_state")
    public void setFiasActualityState(String fiasActualityState) {
        this.fiasActualityState = fiasActualityState;
    }

    @JsonProperty("kladr_id")
    public String getKladrId() {
        return kladrId;
    }

    @JsonProperty("kladr_id")
    public void setKladrId(String kladrId) {
        this.kladrId = kladrId;
    }

    @JsonProperty("geoname_id")
    public String getGeonameId() {
        return geonameId;
    }

    @JsonProperty("geoname_id")
    public void setGeonameId(String geonameId) {
        this.geonameId = geonameId;
    }

    @JsonProperty("capital_marker")
    public String getCapitalMarker() {
        return capitalMarker;
    }

    @JsonProperty("capital_marker")
    public void setCapitalMarker(String capitalMarker) {
        this.capitalMarker = capitalMarker;
    }

    @JsonProperty("okato")
    public String getOkato() {
        return okato;
    }

    @JsonProperty("okato")
    public void setOkato(String okato) {
        this.okato = okato;
    }

    @JsonProperty("oktmo")
    public String getOktmo() {
        return oktmo;
    }

    @JsonProperty("oktmo")
    public void setOktmo(String oktmo) {
        this.oktmo = oktmo;
    }

    @JsonProperty("tax_office")
    public String getTaxOffice() {
        return taxOffice;
    }

    @JsonProperty("tax_office")
    public void setTaxOffice(String taxOffice) {
        this.taxOffice = taxOffice;
    }

    @JsonProperty("tax_office_legal")
    public String getTaxOfficeLegal() {
        return taxOfficeLegal;
    }

    @JsonProperty("tax_office_legal")
    public void setTaxOfficeLegal(String taxOfficeLegal) {
        this.taxOfficeLegal = taxOfficeLegal;
    }

    @JsonProperty("timezone")
    public String getTimezone() {
        return timezone;
    }

    @JsonProperty("timezone")
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @JsonProperty("geo_lat")
    public String getGeoLat() {
        return geoLat;
    }

    @JsonProperty("geo_lat")
    public void setGeoLat(String geoLat) {
        this.geoLat = geoLat;
    }

    @JsonProperty("geo_lon")
    public String getGeoLon() {
        return geoLon;
    }

    @JsonProperty("geo_lon")
    public void setGeoLon(String geoLon) {
        this.geoLon = geoLon;
    }

    @JsonProperty("beltway_hit")
    public Object getBeltwayHit() {
        return beltwayHit;
    }

    @JsonProperty("beltway_hit")
    public void setBeltwayHit(Object beltwayHit) {
        this.beltwayHit = beltwayHit;
    }

    @JsonProperty("beltway_distance")
    public Object getBeltwayDistance() {
        return beltwayDistance;
    }

    @JsonProperty("beltway_distance")
    public void setBeltwayDistance(Object beltwayDistance) {
        this.beltwayDistance = beltwayDistance;
    }

    @JsonProperty("metro")
    public Object getMetro() {
        return metro;
    }

    @JsonProperty("metro")
    public void setMetro(Object metro) {
        this.metro = metro;
    }

    @JsonProperty("qc_geo")
    public String getQcGeo() {
        return qcGeo;
    }

    @JsonProperty("qc_geo")
    public void setQcGeo(String qcGeo) {
        this.qcGeo = qcGeo;
    }

    @JsonProperty("qc_complete")
    public Object getQcComplete() {
        return qcComplete;
    }

    @JsonProperty("qc_complete")
    public void setQcComplete(Object qcComplete) {
        this.qcComplete = qcComplete;
    }

    @JsonProperty("qc_house")
    public Object getQcHouse() {
        return qcHouse;
    }

    @JsonProperty("qc_house")
    public void setQcHouse(Object qcHouse) {
        this.qcHouse = qcHouse;
    }

    @JsonProperty("history_values")
    public Object getHistoryValues() {
        return historyValues;
    }

    @JsonProperty("history_values")
    public void setHistoryValues(Object historyValues) {
        this.historyValues = historyValues;
    }

    @JsonProperty("unparsed_parts")
    public Object getUnparsedParts() {
        return unparsedParts;
    }

    @JsonProperty("unparsed_parts")
    public void setUnparsedParts(Object unparsedParts) {
        this.unparsedParts = unparsedParts;
    }

    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

    @JsonProperty("qc")
    public String getQc() {
        return qc;
    }

    @JsonProperty("qc")
    public void setQc(String qc) {
        this.qc = qc;
    }

}
