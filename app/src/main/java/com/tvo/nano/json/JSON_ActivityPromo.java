package com.tvo.nano.json;

import com.tvo.nano.models.PromoDetails;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Son on 05-May-15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JSON_ActivityPromo {
    @JsonProperty("promodetails")
    private ArrayList<PromoDetails> arrPromoDetails;
    @JsonProperty("storeid")
    private String storeid;
    @JsonProperty("storename")
    private String storename;

    public ArrayList<PromoDetails> getArrPromoDetails() {
        return arrPromoDetails;
    }

    public void setArrPromoDetails(ArrayList<PromoDetails> arrPromoDetails) {
        this.arrPromoDetails = arrPromoDetails;
    }

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
