package com.tvo.nano.json;

import com.tvo.nano.models.Customer;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Son on 2015-06-11.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JSON_SearchCustomerResult {
    @JsonProperty("customerprofiles")
    private ArrayList<Customer> arrCustomers;
    @JsonProperty("message")
    private String message;
    @JsonProperty("sessionid")
    private String sessionid;
    @JsonProperty("status")
    private String status;
    @JsonProperty("statustext")
    private String statustext;
    @JsonProperty("timestamp")
    private String timestamp;

    public ArrayList<Customer> getArrCustomers() {
        return arrCustomers;
    }

    public void setArrCustomers(ArrayList<Customer> arrCustomers) {
        this.arrCustomers = arrCustomers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
