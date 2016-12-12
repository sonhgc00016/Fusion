package com.tvo.nano.json;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Son on 24-Apr-15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JSON_StarsLogoutResult {
    @JsonProperty("status")
    private String status;
    @JsonProperty("statustext")
    private String statustext;
    @JsonProperty("timestamp")
    private String timestamp;

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
