package com.tvo.nano.models;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Son on 22-Apr-15.
 */
public class BAdetails {
    @JsonProperty("userid")
    private String userid;
    @JsonProperty("fullname")
    private String fullname;
    @JsonProperty("mobilenumber")
    private String mobilenumber;
    @JsonProperty("user_id_type")
    private String user_id_type;
    @JsonProperty("user_id_number")
    private String user_id_number;
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("work_hrs_week")
    private String work_hrs_week;
    @JsonProperty("offday")
    private String offday;
    @JsonProperty("manager_team")
    private String manager_team;
    @JsonProperty("role")
    private String role;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getUser_id_type() {
        return user_id_type;
    }

    public void setUser_id_type(String user_id_type) {
        this.user_id_type = user_id_type;
    }

    public String getUser_id_number() {
        return user_id_number;
    }

    public void setUser_id_number(String user_id_number) {
        this.user_id_number = user_id_number;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWork_hrs_week() {
        return work_hrs_week;
    }

    public void setWork_hrs_week(String work_hrs_week) {
        this.work_hrs_week = work_hrs_week;
    }

    public String getOffday() {
        return offday;
    }

    public void setOffday(String offday) {
        this.offday = offday;
    }

    public String getManager_team() {
        return manager_team;
    }

    public void setManager_team(String manager_team) {
        this.manager_team = manager_team;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
