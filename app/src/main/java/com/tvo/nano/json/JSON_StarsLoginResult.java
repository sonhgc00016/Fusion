package com.tvo.nano.json;

import com.tvo.nano.models.BAdetails;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Son on 23-Apr-15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JSON_StarsLoginResult {
    @JsonProperty("BAdetails")
    private BAdetails baDetails;
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

    public BAdetails getBaDetails() {
        return baDetails;
    }

    public void setBaDetails(BAdetails baDetails) {
        this.baDetails = baDetails;
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
