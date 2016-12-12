package com.tvo.nano.fusioncosmetics.activities;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tvo.nano.fusioncosmetics.Constants;
import com.tvo.nano.fusioncosmetics.R;
import com.tvo.nano.fusioncosmetics.fragments.HomeFragment;
import com.tvo.nano.fusioncosmetics.fragments.ItemSoldFragment;
import com.tvo.nano.fusioncosmetics.fragments.LoginFragment;
import com.tvo.nano.fusioncosmetics.fragments.MembershipFragment;
import com.tvo.nano.fusioncosmetics.fragments.SalesTrackingFragment;
import com.tvo.nano.fusioncosmetics.fragments.SearchFragment;
import com.tvo.nano.json.JSON_StarsLogoutResult;
import com.tvo.nano.json.JSON_SubmitSalesResult;
import com.tvo.nano.models.Nationality;
import com.tvo.nano.models.Products;
import com.tvo.nano.models.Race;
import com.tvo.nano.network.CustomAlertDialogManager;
import com.tvo.nano.network.RequestAPI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.tvo.nano.submitsales.Product;
import com.tvo.nano.submitsales.SalesData;
import com.tvo.nano.submitsales.SubmitSales;

public class FrameContentActivity extends FragmentActivity implements View.OnClickListener, RequestAPI.IResultApiListener {

    private ImageView imgLeft, imgRight;
    private TextView tvHeadingPage, tvItemSoldCount;
    private CustomAlertDialogManager dialogManager = new CustomAlertDialogManager();
    private String sessionid, timestamp, name, userid, dob, workinghours, offday, role, teammanager, contact, password,
            salesdate, storename, salesdatecut, user_id_number;

    private ArrayList<Nationality> arrNationalities = new ArrayList<>();
    private ArrayList<Race> arrRaces = new ArrayList<>();

    protected OnBackPressedListener onBackPressedListener;

    public OnBackPressedListener getOnBackPressedListener() {
        return onBackPressedListener;
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    public ArrayList<Nationality> getArrNationalities() {
        return arrNationalities;
    }

    public ArrayList<Race> getArrRaces() {
        return arrRaces;
    }

    public String getUser_id_number() {
        return user_id_number;
    }

    public String getSessionid() {
        return sessionid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getName() {
        return name;
    }

    public String getUserid() {
        return userid;
    }

    public String getDob() {
        return dob;
    }

    public String getWorkinghours() {
        return workinghours;
    }

    public String getOffday() {
        return offday;
    }

    public String getRole() {
        return role;
    }

    public String getTeammanager() {
        return teammanager;
    }

    public String getContact() {
        return contact;
    }

    public String getPassword() {
        return password;
    }

    public void setSalesdatecut(String salesdatecut) {
        this.salesdatecut = salesdatecut;
    }

    public String getSalesdate() {
        return salesdate;
    }

    public String getSalesdatecut() {
        return salesdatecut;
    }

    public String getStorename() {
        return storename;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framecontent);

        //StrictMode
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        imgLeft = (ImageView) findViewById(R.id.actionbar_imgLeft);
        imgLeft.setOnClickListener(this);
        imgRight = (ImageView) findViewById(R.id.actionbar_imgRight);
        imgRight.setOnClickListener(this);
        tvHeadingPage = (TextView) findViewById(R.id.actionbar_tvHeadingPage);
        tvItemSoldCount = (TextView) findViewById(R.id.actionbar_tvItemSoldCount);

        //add first fragment
        addFragment(LoginFragment.class);
    }

    public void addFragment(Class<?> cls) {
        Fragment fm;
        try {
            fm = (Fragment) cls.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_frame, fm, cls.getName()).commit();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setHeadingPage(int id) {
        tvHeadingPage.setText(getString(id));
    }

    public void setImageLeftActionbarBackground(int id) {
        imgLeft.setBackgroundResource(id);
    }

    public void setImageRightActionbarBackground(int id) {
        imgRight.setBackgroundResource(id);
    }

    public void setItemSoldCountVisible(boolean isVisible) {
        if (isVisible) {
            tvItemSoldCount.setVisibility(View.VISIBLE);
        } else {
            tvItemSoldCount.setVisibility(View.GONE);
        }
    }

    public void setItemSoldCount(String count) {
        tvItemSoldCount.setText(count);
    }

    public void replaceFragment(Fragment fm, boolean addToBackStack) {
        //save infomation if login successful
        if (fm instanceof HomeFragment && Constants.stayPage == Constants.StayPage.LOGIN) {
            LoginFragment fLogin = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
            sessionid = fLogin.getSessionid();
            timestamp = fLogin.getTimestamp();
            name = fLogin.getName();
            userid = fLogin.getUserid();
            dob = fLogin.getDob();
            workinghours = fLogin.getWorkinghours();
            offday = fLogin.getOffday();
            role = fLogin.getRole();
            teammanager = fLogin.getTeammanager();
            contact = fLogin.getContact();
            password = fLogin.getPassword();
            user_id_number = fLogin.getUser_id_number();
        }

        if (addToBackStack) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fm, fm.getClass().getName()).addToBackStack(fm.getClass().getName())
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fm, fm.getClass().getName())
                    .commit();
        }
    }

    private void imgLeftClicked() {
        if (Constants.stayPage != Constants.StayPage.HOME
                && Constants.stayPage != Constants.StayPage.LOGIN) {
            //remove all in backstack before redirect to Home Fragment
            //fix bug overlap layout myprofile and home
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
            if (fragment != null)
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            getSupportFragmentManager().popBackStack();
        }
    }

    private void imgRightClicked() {
        if (Constants.stayPage == Constants.StayPage.HOME) {
            //Home page Logout clicked
            logOut(userid, sessionid, timestamp);

        } else if (Constants.stayPage == Constants.StayPage.SALES_TRACKING) {
            int itemsoldCount = Integer.parseInt(tvItemSoldCount.getText().toString());
            if (itemsoldCount > 0) {
                //Sales Stracking page ItemSold clicked
                SalesTrackingFragment fSalesTracking =
                        (SalesTrackingFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
                salesdate = fSalesTracking.getEdtDate().getText().toString().trim();
                storename = fSalesTracking.getStorename();
                salesdatecut = salesdate.replaceAll("[^a-zA-Z0-9.-]", "");
                if (validateDate(salesdate)) {
                    replaceFragment(new ItemSoldFragment(), true);
                }
            } else {
                return;
            }
        } else if (Constants.stayPage == Constants.StayPage.ITEM_SOLD) {
            //Item Sold page Submit clicked
            submitSales();
        } else if (Constants.stayPage == Constants.StayPage.MEMBERSHIP) {
            //Membership page Search clicked
            MembershipFragment fMembership =
                    (MembershipFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
            arrNationalities = fMembership.getArrNationalities();
            arrRaces = fMembership.getArrRaces();

            replaceFragment(new SearchFragment(), true);
        }
    }

    /**
     * submit sales after user enter quantity of product
     */
    private void submitSales() {
        final String URL = Constants.SERVER_URL + "SubmitSales";

        Constants.submitSales = convertToSubmitSalesObject();
        Constants.submitSales.setUserid(userid);
        Constants.submitSales.setSessionid(sessionid);
        Constants.submitSales.setTimestamp(timestamp);
        Constants.submitSales.setSalesdate(salesdate);

        RequestAPI.requestAPI(this, URL, null, Request.Method.POST,
                JSON_SubmitSalesResult.class, this, 2, "SubmitSalesResult");
    }

    public void decodeAndReadJson(String salesdatecut) {
        String jsonString = null;
        try {
            File file = new File("/sdcard/Download/" + userid + salesdatecut);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                String result = convertStreamToString(fis);
                //Make sure you close all streams.
                fis.close();

                // decode string before get data
                byte[] data = Base64.decode(result, Base64.DEFAULT);
                jsonString = new String(data, getString(R.string.utf8));
                Gson gson = new Gson();

                //convert string to ArrayList<Products>
                Type type = new TypeToken<ArrayList<Products>>() {
                }.getType();
                Constants.arrItemSold = gson.fromJson(jsonString, type);
            } else {
                Constants.arrItemSold = new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void encodeAndSaveJson() {
        try {
            Gson gson = new Gson();
            //parse data arrayList to jsonString
            String jsonString = gson.toJson(Constants.arrItemSold);
            // encode string before save
            byte[] data = jsonString.getBytes("UTF-8");
            String encodeItemSold = Base64.encodeToString(data, Base64.DEFAULT);

            //write converted json data to a file named "salesdatecut.json"
            FileWriter writer =
                    new FileWriter("/sdcard/Download/" + userid + salesdatecut);
            writer.write(encodeItemSold);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param is
     * @return String of file
     * @throws Exception
     */
    private String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        reader.close();
        return stringBuilder.toString();
    }

    /**
     * validate Date before Add Item or click Item Sold
     *
     * @param dateString
     * @return
     */
    public boolean validateDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat(getString(R.string.date_format), Locale.ROOT);
        Date datePicked = null;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterdayString = format.format(cal.getTime());
        int currentHours = new Time(System.currentTimeMillis()).getHours();
        Date yesterday = null;
        try {
            datePicked = format.parse(dateString);
            yesterday = format.parse(yesterdayString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (currentHours >= 11 && datePicked.getTime() == yesterday.getTime()) {//user picked yesterday after 11am
            dialogManager.showCustomAlertDialog(this, getString(R.string.error), getString(R.string.after_11am), false);
        } else {
            return true;
        }
        return false;
    }

    private void logOut(String userid, String sessionid, String timestamp) {
        final String URL = Constants.SERVER_URL + getString(R.string.stars_logout);

        Map<String, String> params = new HashMap<>();
        params.put("userid", userid);
        params.put("sessionid", sessionid);
        params.put("timestamp", timestamp);

        RequestAPI.requestAPI(this, URL, params, Request.Method.POST,
                JSON_StarsLogoutResult.class, this, 2, "StarsLogoutResult");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.actionbar_imgLeft:
                imgLeftClicked();
                break;
            case R.id.actionbar_imgRight:
                imgRightClicked();
                break;
            default:
                break;
        }
    }

    @Override
    public void onResult(Object object) {
        if (object != null) {
            if (object instanceof JSON_StarsLogoutResult) {
                replaceFragment(new LoginFragment(), false);
            } else if (object instanceof JSON_SubmitSalesResult) {
                JSON_SubmitSalesResult result = (JSON_SubmitSalesResult) object;
                if (result.getStatus().equals("0")) {
                    Constants.arrItemSold = new ArrayList<>();
                    encodeAndSaveJson();
                    dialogManager.showCustomAlertDialog(this, getString(R.string.info),
                            result.getMessage(), false);
                    replaceFragment(new HomeFragment(), false);
                } else {
                    if (result.getMessage() != null && !result.getMessage().isEmpty()) {
                        dialogManager.showCustomAlertDialog(this, getString(R.string.error),
                                result.getMessage(), true);
                    } else {
                        dialogManager.showCustomAlertDialog(this, getString(R.string.error), getString(R.string.something_wrong2), true);
                    }
                }
            }
        }
    }

    private SubmitSales convertToSubmitSalesObject() {

        SubmitSales submitSales = Constants.submitSales;
        ArrayList<SalesData> arrSalesData = new ArrayList<>();
        submitSales.setSalesData(arrSalesData);

        Map<String, ArrayList<Products>> itemSoldByStoreMap = new HashMap<>();
        ArrayList<Products> arrItemSold = Constants.arrItemSold;

        ArrayList<String> arrStoreName = new ArrayList<>();
        for (Products product : arrItemSold) {
            String storename = product.getStorename();
            if (!itemSoldByStoreMap.containsKey(storename)) {
                ArrayList<Products> arrItemSoldByStore = new ArrayList<>();
                arrItemSoldByStore.add(product);
                itemSoldByStoreMap.put(storename, arrItemSoldByStore);
                arrStoreName.add(storename);
            } else {
                itemSoldByStoreMap.get(storename).add(product);
            }
        }

        decodeAndReadJson(salesdatecut);

        if (Constants.arrItemSold.size() > 0 && arrStoreName.size() > 0) {
            for (String store : arrStoreName) {
                ArrayList<Products> arrItemSoldByStore;
                arrItemSoldByStore = itemSoldByStoreMap.get(store);

                //set storeid and storename for salesdata
                SalesData salesData = new SalesData();
                salesData.setStoreid(arrItemSoldByStore.get(0).getStoreid());
                salesData.setStorename(store);
                ArrayList<Product> arrProduct = new ArrayList<>();

                for (Products pro : arrItemSoldByStore) {
                    Product product = new Product();
                    product.setProductid(pro.getProductid());
                    product.setCategory_id(pro.getCategory_id());
                    product.setProd_brand_id(pro.getBrand_id());
                    product.setProd_price(pro.getPrice());
                    product.setSalesfig(pro.getQuantity());
                    arrProduct.add(product);
                }
                salesData.setSalesProducts(arrProduct);
                arrSalesData.add(salesData);
            }
        }
        return submitSales;
    }

    public interface OnBackPressedListener {
        public void doBack();
    }

    public class BaseBackPressedListener implements OnBackPressedListener {
        private final FragmentActivity activity;

        public BaseBackPressedListener(FragmentActivity activity) {
            this.activity = activity;
        }

        @Override
        public void doBack() {
            activity.getSupportFragmentManager().popBackStack(null, 0);
        }
    }

    @Override
    public void onBackPressed() {
        if (Constants.stayPage == Constants.StayPage.HOME) {
            System.exit(1);
        } else if (onBackPressedListener != null) {
            onBackPressedListener.doBack();
        } else
            super.onBackPressed();
    }
}
