package com.tvo.nano.fusioncosmetics;

import com.tvo.nano.json.JSON_RegisterCustomer;
import com.tvo.nano.models.Products;
import com.tvo.nano.submitsales.SubmitSales;

import java.util.ArrayList;

/**
 * Created by Son on 16-Apr-15.
 */
public class Constants {
    public enum StayPage {
        LOGIN, HOME, MY_PROFILE, DUTY_ROSTER, SALES_TRACKING, ITEM_SOLD,
        REPORT, CHANGE_PASSWORD, MEMBERSHIP, SEARCH, MEMBERSHIP_PROFILE_UPDATE
    }

    ;

    public static int itemSold = 0;
    public static ArrayList<Products> arrItemSold = new ArrayList<>();

    public static SubmitSales submitSales = new SubmitSales();

    public static StayPage stayPage = StayPage.LOGIN;

    public static final String SERVER_URL = "http://52.74.68.67/FusionSTARSAPI/StarsAPI.svc/";

    public static final String KEY_EXTRA_SESSIONID = "KEY_EXTRA_SESSIONID";
    public static final String KEY_EXTRA_TIME_STAMP = "KEY_EXTRA_TIME_STAMP";
    public static final String KEY_EXTRA_USERID = "KEY_EXTRA_USERID";

    //key extra Membership
    public static final String KEY_EXTRA_CUSTOMER_ID = "KEY_EXTRA_CUSTOMER_ID";
    public static final String KEY_EXTRA_SALUTATION = "KEY_EXTRA_SALUTATION";
    public static final String KEY_EXTRA_FULL_NAME = "KEY_EXTRA_FULL_NAME";
    public static final String KEY_EXTRA_GENDER = "KEY_EXTRA_GENDER";
    public static final String KEY_EXTRA_NRIC_FIN = "KEY_EXTRA_NRIC_FIN";
    public static final String KEY_EXTRA_DOB = "KEY_EXTRA_DOB";
    public static final String KEY_EXTRA_NATIONALITY_ID = "KEY_EXTRA_NATIONALITY_STRING";
    public static final String KEY_EXTRA_RACE_ID = "KEY_EXTRA_RACE_STRING";
    public static final String KEY_EXTRA_EMAIL = "KEY_EXTRA_EMAIL";
    public static final String KEY_EXTRA_ADDRESS = "KEY_EXTRA_ADDRESS";
    public static final String KEY_EXTRA_POSTAL_CODE = "KEY_EXTRA_POSTAL_CODE";
    public static final String KEY_EXTRA_HANDPHONE = "KEY_EXTRA_HANDPHONE";
    public static final String KEY_EXTRA_HOME_OFFICE = "KEY_EXTRA_HOME_OFFICE";
    public static final String KEY_EXTRA_STATUS = "KEY_EXTRA_STATUS";
}
