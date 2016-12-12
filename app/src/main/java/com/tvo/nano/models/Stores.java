package com.tvo.nano.models;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Son on 23-Apr-15.
 */

public class Stores {
    @JsonProperty("storeid")
    protected String storeid;
    @JsonProperty("storename")
    protected String storename;

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }
}
