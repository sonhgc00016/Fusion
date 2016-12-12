package com.tvo.nano.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Son on 2015-06-10.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {
    @JsonProperty("customer_id")
    private String customer_id;
    @JsonProperty("membership_number")
    private String membership_number;
    @JsonProperty("salutation")
    private String salutation;
    @JsonProperty("fullname")
    private String fullname;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("nric_pass_number")
    private String nric_pass_number;
    @JsonProperty("dateofbirth")
    private String dateofbirth;
    @JsonProperty("nationality_id")
    private String nationality_id;
    @JsonProperty("race_id")
    private String race_id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("address")
    private String address;
    @JsonProperty("postalcode")
    private String postalcode;
    @JsonProperty("handphone")
    private String handphone;
    @JsonProperty("homephone")
    private String homephone;
    @JsonProperty("maritalstatus")
    private String maritalstatus;
    @JsonProperty("is_acceptance")
    private String is_acceptance;
    @JsonProperty("dateofissue")
    private String dateofissue;
    @JsonProperty("cust_status")
    private String cust_status;

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getMembership_number() {
        return membership_number;
    }

    public void setMembership_number(String membership_number) {
        this.membership_number = membership_number;
    }

    public String getDateofissue() {
        return dateofissue;
    }

    public void setDateofissue(String dateofissue) {
        this.dateofissue = dateofissue;
    }

    public String getCust_status() {
        return cust_status;
    }

    public void setCust_status(String cust_status) {
        this.cust_status = cust_status;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNric_pass_number() {
        return nric_pass_number;
    }

    public void setNric_pass_number(String nric_pass_number) {
        this.nric_pass_number = nric_pass_number;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getNationality_id() {
        return nationality_id;
    }

    public void setNationality_id(String nationality_id) {
        this.nationality_id = nationality_id;
    }

    public String getRace_id() {
        return race_id;
    }

    public void setRace_id(String race_id) {
        this.race_id = race_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getHandphone() {
        return handphone;
    }

    public void setHandphone(String handphone) {
        this.handphone = handphone;
    }

    public String getHomephone() {
        return homephone;
    }

    public void setHomephone(String homephone) {
        this.homephone = homephone;
    }

    public String getMaritalstatus() {
        return maritalstatus;
    }

    public void setMaritalstatus(String maritalstatus) {
        this.maritalstatus = maritalstatus;
    }

    public String getIs_acceptance() {
        return is_acceptance;
    }

    public void setIs_acceptance(String is_acceptance) {
        this.is_acceptance = is_acceptance;
    }
}
