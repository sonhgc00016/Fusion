package com.tvo.nano.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Son on 21-Apr-15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Products extends Stores {
    @JsonProperty("barcodenumber")
    private String barcodenumber;
    @JsonProperty("brand_id")
    private String brand_id;
    @JsonProperty("brand_name")
    private String brand_name;
    @JsonProperty("category_id")
    private String category_id;
    @JsonProperty("category_name")
    private String category_name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("price")
    private String price;
    @JsonProperty("proditem_id")
    private String proditem_id;
    @JsonProperty("productid")
    private String productid;
    @JsonProperty("size")
    private String size;
    @JsonProperty("subbrand_id")
    private String subbrand_id;
    @JsonProperty("subbrand_name")
    private String subbrand_name;
    private String quantity;

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getBarcodenumber() {
        return barcodenumber;
    }

    public void setBarcodenumber(String barcodenumber) {
        this.barcodenumber = barcodenumber;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProditem_id() {
        return proditem_id;
    }

    public void setProditem_id(String proditem_id) {
        this.proditem_id = proditem_id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSubbrand_id() {
        return subbrand_id;
    }

    public void setSubbrand_id(String subbrand_id) {
        this.subbrand_id = subbrand_id;
    }

    public String getSubbrand_name() {
        return subbrand_name;
    }

    public void setSubbrand_name(String subbrand_name) {
        this.subbrand_name = subbrand_name;
    }
}
