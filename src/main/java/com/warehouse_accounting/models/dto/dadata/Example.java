
package com.warehouse_accounting.models.dto.dadata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "suggestions"
})
@Generated("jsonschema2pojo")
public class Example {

    @JsonProperty("suggestions")
    private List<Suggestion> suggestions = null;

    @JsonProperty("suggestions")
    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    @JsonProperty("suggestions")
    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }

}
