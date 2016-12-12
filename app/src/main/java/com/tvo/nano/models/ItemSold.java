package com.tvo.nano.models;

import java.util.ArrayList;

/**
 * Created by Son on 14-May-15.
 */
public class ItemSold extends Stores {

    public String getSalesdate() {
        return salesdate;
    }

    public void setSalesdate(String salesdate) {
        this.salesdate = salesdate;
    }

    private String salesdate;

    public ItemSold(String salesdate, String storeid, String storename, Products product) {
        this.salesdate = salesdate;
        this.storeid = storeid;
        this.storename = storename;
        this.product = product;
    }

    private Products product;

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }
}
