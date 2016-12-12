package com.tvo.nano.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Son on 05-May-15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PromoDetails {
    @JsonProperty("dateinfo")
    private String dateinfo;
    @JsonProperty("description")
    private String description;
    @JsonProperty("product")
    private String productDescription;

    public String getDateinfo() {
        return dateinfo;
    }

    public void setDateinfo(String dateinfo) {
        this.dateinfo = dateinfo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}
