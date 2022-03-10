
package com.warehouse_accounting.models.dto.dadata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "value",
    "unrestricted_value",
    "data"
})
@Generated("jsonschema2pojo")
public class Suggestion {

    @JsonProperty("value")
    private String value;
    @JsonProperty("unrestricted_value")
    private String unrestrictedValue;
    @JsonProperty("data")
    private Data data;

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    @JsonProperty("unrestricted_value")
    public String getUnrestrictedValue() {
        return unrestrictedValue;
    }

    @JsonProperty("unrestricted_value")
    public void setUnrestrictedValue(String unrestrictedValue) {
        this.unrestrictedValue = unrestrictedValue;
    }

    @JsonProperty("data")
    public Data getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(Data data) {
        this.data = data;
    }

}
