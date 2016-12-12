package com.tvo.nano.submitsales;

import com.tvo.nano.models.Stores;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by Son on 2015-05-21.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SalesData extends Stores {
    ArrayList<Product> SalesProducts;

    public ArrayList<Product> getSalesProducts() {
        return SalesProducts;
    }

    public void setSalesProducts(ArrayList<Product> salesProducts) {
        this.SalesProducts = salesProducts;
    }
}
