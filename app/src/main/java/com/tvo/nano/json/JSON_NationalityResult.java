package com.tvo.nano.json;

import com.tvo.nano.models.Nationality;
import com.tvo.nano.models.Race;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Son on 2015-06-10.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JSON_NationalityResult {
    @JsonProperty("message")
    private String message;
    @JsonProperty("nationality")
    private ArrayList<Nationality> nationalities;
    @JsonProperty("sessionid")
    private String sessionid;
    @JsonProperty("status")
    private String status;
    @JsonProperty("statustext")
    private String statustext;
    @JsonProperty("timestamp")
    private String timestamp;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Nationality> getNationalities() {
        return nationalities;
    }

    public void setNationalities(ArrayList<Nationality> nationalities) {
        this.nationalities = nationalities;
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
