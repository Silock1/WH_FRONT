
package com.warehouse_accounting.models.dto.dadata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "full_with_opf",
    "short_with_opf",
    "latin",
    "full",
    "short"
})
@Generated("jsonschema2pojo")
public class Name {

    @JsonProperty("full_with_opf")
    private String fullWithOpf;
    @JsonProperty("short_with_opf")
    private String shortWithOpf;
    @JsonProperty("latin")
    private Object latin;
    @JsonProperty("full")
    private String full;
    @JsonProperty("short")
    private String _short;

    @JsonProperty("full_with_opf")
    public String getFullWithOpf() {
        return fullWithOpf;
    }

    @JsonProperty("full_with_opf")
    public void setFullWithOpf(String fullWithOpf) {
        this.fullWithOpf = fullWithOpf;
    }

    @JsonProperty("short_with_opf")
    public String getShortWithOpf() {
        return shortWithOpf;
    }

    @JsonProperty("short_with_opf")
    public void setShortWithOpf(String shortWithOpf) {
        this.shortWithOpf = shortWithOpf;
    }

    @JsonProperty("latin")
    public Object getLatin() {
        return latin;
    }

    @JsonProperty("latin")
    public void setLatin(Object latin) {
        this.latin = latin;
    }

    @JsonProperty("full")
    public String getFull() {
        return full;
    }

    @JsonProperty("full")
    public void setFull(String full) {
        this.full = full;
    }

    @JsonProperty("short")
    public String getShort() {
        return _short;
    }

    @JsonProperty("short")
    public void setShort(String _short) {
        this._short = _short;
    }

}
