package com.tvo.nano.json;

import com.tvo.nano.models.Customer;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Son on 2015-06-10.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JSON_RegisterCustomer {
    @JsonProperty("userid")
    private String userid;
    @JsonProperty("sessionid")
    private String sessionid;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("customerprofile")
    private Customer customerprofile;

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

    public Customer getCustomerprofile() {
        return customerprofile;
    }

    public void setCustomerprofile(Customer customerprofile) {
        this.customerprofile = customerprofile;
    }
}
