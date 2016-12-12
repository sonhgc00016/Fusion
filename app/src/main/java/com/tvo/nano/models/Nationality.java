package com.tvo.nano.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Son on 2015-06-10.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Nationality {
    @JsonProperty("nationality_id")
    private String nationality_id;
    @JsonProperty("nationality_name")
    private String nationality_name;

    public String getNationality_id() {
        return nationality_id;
    }

    public void setNationality_id(String nationality_id) {
        this.nationality_id = nationality_id;
    }

    public String getNationality_name() {
        return nationality_name;
    }

    public void setNationality_name(String nationality_name) {
        this.nationality_name = nationality_name;
    }
}
