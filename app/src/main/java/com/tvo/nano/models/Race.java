package com.tvo.nano.models;

/**
 * Created by Son on 2015-06-10.
 */

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Race {
    @JsonProperty("race_id")
    private String race_id;
    @JsonProperty("race_name")
    private String race_name;

    public String getRace_id() {
        return race_id;
    }

    public void setRace_id(String race_id) {
        this.race_id = race_id;
    }

    public String getRace_name() {
        return race_name;
    }

    public void setRace_name(String race_name) {
        this.race_name = race_name;
    }
}
