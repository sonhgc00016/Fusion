package com.tvo.nano.json;

import com.tvo.nano.models.Products;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Son on 24-Apr-15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JSON_ProductsResult {
    @JsonProperty("Products")
    private ArrayList<Products> arrProducts;
    @JsonProperty("sessionid")
    private String sessionid;
    @JsonProperty("status")
    private String status;
    @JsonProperty("statustext")
    private String statustext;
    @JsonProperty("timestamp")
    private String timestamp;

    public ArrayList<Products> getArrProducts() {
        return arrProducts;
    }

    public void setArrProducts(ArrayList<Products> arrProducts) {
        this.arrProducts = arrProducts;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatustext() {
        return statustext;
    }

    public void setStatustext(String statustext) {
        this.statustext = statustext;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
