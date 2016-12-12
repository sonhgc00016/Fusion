package com.tvo.nano.submitsales;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Son on 2015-05-22.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    @JsonProperty("productid")
    private String productid;
    @JsonProperty("salesfig")
    private String salesfig;
    @JsonProperty("prod_price")
    private String prod_price;
    @JsonProperty("prod_brand_id")
    private String prod_brand_id;
    @JsonProperty("category_id")
    private String category_id;

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getSalesfig() {
        return salesfig;
    }

    public void setSalesfig(String salesfig) {
        this.salesfig = salesfig;
    }

    public String getProd_price() {
        return prod_price;
    }

    public void setProd_price(String prod_price) {
        this.prod_price = prod_price;
    }

    public String getProd_brand_id() {
        return prod_brand_id;
    }

    public void setProd_brand_id(String prod_brand_id) {
        this.prod_brand_id = prod_brand_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }
}
