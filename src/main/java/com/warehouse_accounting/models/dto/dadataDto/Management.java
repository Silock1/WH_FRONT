
package com.warehouse_accounting.models.dto.dadataDto;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Management {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("post")
    @Expose
    private String post;
    @SerializedName("disqualified")
    @Expose
    private Object disqualified;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Object getDisqualified() {
        return disqualified;
    }

    public void setDisqualified(Object disqualified) {
        this.disqualified = disqualified;
    }

}
