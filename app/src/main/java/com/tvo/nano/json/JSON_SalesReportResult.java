package com.tvo.nano.json;

import com.tvo.nano.models.PromoDetails;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Son on 05-May-15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JSON_SalesReportResult {
    @JsonProperty("activityPromo")
    private ArrayList<JSON_ActivityPromo> arrActivityPromo;
    @JsonProperty("salesreport")
    private ArrayList<JSON_SalesReport> arrSalesreport;
    @JsonProperty("sessionid")
    private String sessionid;
    @JsonProperty("status")
    private String status;
    @JsonProperty("statustext")
    private String statustext;
    @JsonProperty("timestamp")
    private String timestamp;

    public ArrayList<JSON_ActivityPromo> getArrActivityPromo() {
        return arrActivityPromo;
    }

    public void setArrActivityPromo(ArrayList<JSON_ActivityPromo> arrActivityPromo) {
        this.arrActivityPromo = arrActivityPromo;
    }

    public ArrayList<JSON_SalesReport> getArrSalesreport() {
        return arrSalesreport;
    }

    public void setArrSalesreport(ArrayList<JSON_SalesReport> arrSalesreport) {
        this.arrSalesreport = arrSalesreport;
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
