package com.tvo.nano.submitsales;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Son on 2015-05-21.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubmitSales {
    @JsonProperty("userid")
    private String userid;
    @JsonProperty("sessionid")
    private String sessionid;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("salesdate")
    private String salesdate;
    @JsonProperty("SalesData")
    private ArrayList<SalesData> SalesData;

    public ArrayList<SalesData> getSalesData() {
        return SalesData;
    }

    public void setSalesData(ArrayList<SalesData> salesData) {
        this.SalesData = salesData;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSalesdate() {
        return salesdate;
    }

    public void setSalesdate(String salesdate) {
        this.salesdate = salesdate;
    }
}
