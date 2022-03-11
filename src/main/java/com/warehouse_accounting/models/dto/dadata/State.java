
package com.warehouse_accounting.models.dto.dadata;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class State {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("code")
    @Expose
    private Object code;
    @SerializedName("actuality_date")
    @Expose
    private Long actualityDate;
    @SerializedName("registration_date")
    @Expose
    private Long registrationDate;
    @SerializedName("liquidation_date")
    @Expose
    private Object liquidationDate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public Long getActualityDate() {
        return actualityDate;
    }

    public void setActualityDate(Long actualityDate) {
        this.actualityDate = actualityDate;
    }

    public Long getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Long registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Object getLiquidationDate() {
        return liquidationDate;
    }

    public void setLiquidationDate(Object liquidationDate) {
        this.liquidationDate = liquidationDate;
    }

}
