package com.tvo.nano.json;

import com.tvo.nano.models.RosterReport;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Son on 24-Apr-15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JSON_DutyRosterResult {
    @JsonProperty("RosterReport")
    private ArrayList<RosterReport> arrRosterReport;
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

    public ArrayList<RosterReport> getArrRosterReport() {
        return arrRosterReport;
    }

    public void setArrRosterReport(ArrayList<RosterReport> arrRosterReport) {
        this.arrRosterReport = arrRosterReport;
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
