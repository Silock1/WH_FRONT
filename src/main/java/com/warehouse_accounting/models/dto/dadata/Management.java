
package com.warehouse_accounting.models.dto.dadata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "post",
    "disqualified"
})
@Generated("jsonschema2pojo")
public class Management {

    @JsonProperty("name")
    private String name;
    @JsonProperty("post")
    private String post;
    @JsonProperty("disqualified")
    private Object disqualified;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("post")
    public String getPost() {
        return post;
    }

    @JsonProperty("post")
    public void setPost(String post) {
        this.post = post;
    }

    @JsonProperty("disqualified")
    public Object getDisqualified() {
        return disqualified;
    }

    @JsonProperty("disqualified")
    public void setDisqualified(Object disqualified) {
        this.disqualified = disqualified;
    }

}
