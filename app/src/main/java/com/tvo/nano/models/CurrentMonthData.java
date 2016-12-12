package com.tvo.nano.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Son on 05-May-15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentMonthData {
    @JsonProperty("Achieved")
    private String achieved;
    @JsonProperty("Brand")
    private String brand;
    @JsonProperty("MTD")
    private String mtd;
    @JsonProperty("Target")
    private String target;

    public String getAchieved() {
        return achieved;
    }

    public void setAchieved(String achieved) {
        this.achieved = achieved;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMtd() {
        return mtd;
    }

    public void setMtd(String mtd) {
        this.mtd = mtd;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
