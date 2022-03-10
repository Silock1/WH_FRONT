
package com.warehouse_accounting.models.dto.dadata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "status",
    "code",
    "actuality_date",
    "registration_date",
    "liquidation_date"
})
@Generated("jsonschema2pojo")
public class State {

    @JsonProperty("status")
    private String status;
    @JsonProperty("code")
    private Object code;
    @JsonProperty("actuality_date")
    private Long actualityDate;
    @JsonProperty("registration_date")
    private Long registrationDate;
    @JsonProperty("liquidation_date")
    private Object liquidationDate;

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("code")
    public Object getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(Object code) {
        this.code = code;
    }

    @JsonProperty("actuality_date")
    public Long getActualityDate() {
        return actualityDate;
    }

    @JsonProperty("actuality_date")
    public void setActualityDate(Long actualityDate) {
        this.actualityDate = actualityDate;
    }

    @JsonProperty("registration_date")
    public Long getRegistrationDate() {
        return registrationDate;
    }

    @JsonProperty("registration_date")
    public void setRegistrationDate(Long registrationDate) {
        this.registrationDate = registrationDate;
    }

    @JsonProperty("liquidation_date")
    public Object getLiquidationDate() {
        return liquidationDate;
    }

    @JsonProperty("liquidation_date")
    public void setLiquidationDate(Object liquidationDate) {
        this.liquidationDate = liquidationDate;
    }

}
