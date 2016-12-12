package com.tvo.nano.fusioncosmetics.fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.tvo.nano.adapters.ProductSalesTrackingAdapter;
import com.tvo.nano.fusioncosmetics.Constants;
import com.tvo.nano.fusioncosmetics.activities.FrameContentActivity;
import com.tvo.nano.fusioncosmetics.R;
import com.tvo.nano.json.JSON_ProductsResult;
import com.tvo.nano.json.JSON_StoreAssignResult;
import com.tvo.nano.models.Products;
import com.tvo.nano.models.Stores;
import com.tvo.nano.network.CustomAlertDialogManager;
import com.tvo.nano.network.RequestAPI;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalesTrackingFragment extends BaseAppFragment implements View.OnClickListener, RequestAPI.IResultApiListener {

    private ImageView imgCalendar, imgX;
    private DatePickerDialog picker;
    private SimpleDateFormat dateFormatter;
    private Spinner spnStores;
    private String storeid;
    ArrayList<Stores> arrStores = new ArrayList<>();

    private EditText edtDate, edtKeyword;
    private ListView lvProductInfo;

    private ArrayList<Products> arrProducts;

    private String sessionid, timestamp, userid, storename;

    private CustomAlertDialogManager dialogManager = new CustomAlertDialogManager();

    public String getStoreid() {
        return storeid;
    }

    public EditText getEdtDate() {
        return edtDate;
    }

    public String getStorename() {
        return storename;
    }

    public SalesTrackingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sales_tracking, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //set statying page
        Constants.stayPage = Constants.StayPage.SALES_TRACKING;

        //set Actionbar values
        setHeadingPage(R.string.sales_tracking);
        setImageRightActionbarBackground(R.drawable.actionbar_imgitemsold);
        setImageLeftActionbarBackground(R.drawable.actionbar_imgback);

        spnStores = (Spinner) view.findViewById(R.id.salesTracking_spnStores);
        spnStores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                storename = spnStores.getSelectedItem().toString();
                storeid = arrStores.get(spnStores.getSelectedItemPosition()).getStoreid();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imgCalendar = (ImageView) view.findViewById(R.id.salesTracking_imgCalendar);
        imgCalendar.setOnClickListener(this);

        imgX = (ImageView) view.findViewById(R.id.salesTracking_imgX);
        imgX.setOnClickListener(this);

        //set current Date to edtDate
        dateFormatter = new SimpleDateFormat(getString(R.string.date_format), Locale.ROOT);
        Calendar calendar = Calendar.getInstance();
        edtDate = (EditText) view.findViewById(R.id.salesTracking_edtDate);
        edtDate.setText(dateFormatter.format(calendar.getTime()));

        //catch event DatePickerDialog onDataSet
        picker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newCalendar = Calendar.getInstance();
                newCalendar.set(year, monthOfYear, dayOfMonth);
                edtDate.setText(dateFormatter.format(newCalendar.getTime()));

                setItemSoldCount();
                getStoresAssign(userid, sessionid, timestamp);
            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        int currentHours = new Time(System.currentTimeMillis()).getHours();
        Date datePicked = null;
        try {
            datePicked = dateFormatter.parse(edtDate.getText().toString().trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (currentHours >= 11 && datePicked.getTime() == datePicked.getTime()) {
            picker.getDatePicker().setMinDate(calendar.getTimeInMillis());
            picker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        } else {
            picker.getDatePicker().setMinDate(cal.getTimeInMillis());
            picker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        }

        lvProductInfo = (ListView) view.findViewById(R.id.salesTracking_lvProductInfo);

        //edtKeyword
        edtKeyword = (EditText) view.findViewById(R.id.salesTracking_edtKeyword);
        TextWatcher inputTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (arrProducts != null && arrProducts.size() > 0) {
                    if (s.length() >= 3) {//Start searching after user entered 3 characters or more
                        searchLocalProduct(edtKeyword.getText().toString().trim());
                    } else if (s.length() == 0) {//get all products when keyword is null or empty
                        ProductSalesTrackingAdapter adapter = new ProductSalesTrackingAdapter(getActivity(), arrProducts);
                        lvProductInfo.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        edtKeyword.addTextChangedListener(inputTextWatcher);

        //get user's infomation
        FrameContentActivity frameContentActivity = (FrameContentActivity) getActivity();
        userid = frameContentActivity.getUserid();
        sessionid = frameContentActivity.getSessionid();
        timestamp = frameContentActivity.getTimestamp();


        getStoresAssign(userid, sessionid, timestamp);
        getProductsResult(userid, sessionid, timestamp);
        this.setItemSoldCount();
    }

    private void setItemSoldCount() {
        String salesdatecut = edtDate.getText().toString().trim().replaceAll("[^a-zA-Z0-9.-]", "");
        //get data from file first
        decodeAndReadJson(salesdatecut);

        //then set Item Sold count
        if (Constants.arrItemSold != null || Constants.arrItemSold.size() > 0) {
            Constants.itemSold = Constants.arrItemSold.size();
            setItemSoldCount(String.valueOf(Constants.itemSold));
        } else {
            setItemSoldCount("0");
        }
        setItemSoldCountVisible(true);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.salesTracking_imgCalendar:
                picker.show();
                break;
            case R.id.salesTracking_imgX:
                edtKeyword.setText("");
                break;
            default:
                break;
        }
    }

    private void getStoresAssign(String userid, String sessionid, String timestamp) {
        final String URL = Constants.SERVER_URL + "GetStoresAssign";

        Map<String, String> params = new HashMap<>();
        params.put("userid", userid);
        params.put("sessionid", sessionid);
        params.put("timestamp", timestamp);
        params.put("salesdate", edtDate.getText().toString());

        RequestAPI.requestAPI(getActivity(), URL, params, Request.Method.POST,
                JSON_StoreAssignResult.class, this, 1, "GetStoresAssignResult");
    }

    private void getProductsResult(String userid, String sessionid, String timestamp) {
        final String URL = Constants.SERVER_URL + "Getproducts";

        Map<String, String> params = new HashMap<>();
        params.put("userid", userid);
        params.put("sessionid", sessionid);
        params.put("timestamp", timestamp);

        RequestAPI.requestAPI(getActivity(), URL, params, Request.Method.POST,
                JSON_ProductsResult.class, this, 2, "GetProductsResult");
    }

    private void searchLocalProduct(String keyword) {
        ArrayList<Products> searchResultArrProduct = new ArrayList<>();
        if (keyword != null && !keyword.isEmpty()) {
            for (int i = 0; i < (arrProducts.size() - 1); i++) {
                if (arrProducts.get(i).getProditem_id().toLowerCase().contains(keyword.toLowerCase())
                        || arrProducts.get(i).getDescription().toLowerCase().contains(keyword.toLowerCase())
                        || arrProducts.get(i).getSize().toLowerCase().contains(keyword.toLowerCase())
                        || arrProducts.get(i).getPrice().toLowerCase().contains(keyword.toLowerCase())) {
                    searchResultArrProduct.add(arrProducts.get(i));
                }
            }
            if (searchResultArrProduct.size() > 0) {
                ProductSalesTrackingAdapter adapter = new ProductSalesTrackingAdapter(getActivity(), searchResultArrProduct);
                lvProductInfo.setAdapter(adapter);
            } else {
                ProductSalesTrackingAdapter adapter = new ProductSalesTrackingAdapter(getActivity(), arrProducts);
                lvProductInfo.setAdapter(adapter);
                Toast.makeText(getActivity(), getActivity().getString(R.string.no_result), Toast.LENGTH_SHORT).show();
            }
        } else {
            ProductSalesTrackingAdapter adapter = new ProductSalesTrackingAdapter(getActivity(), arrProducts);
            lvProductInfo.setAdapter(adapter);
        }
    }

    @Override
    public void onResult(Object object) {
        if (object != null && object instanceof JSON_StoreAssignResult) {
            JSON_StoreAssignResult result = (JSON_StoreAssignResult) object;
            ArrayList<String> arrStringStores = new ArrayList<>();
            if (result.getArrStores() != null && result.getArrStores().size() > 0) {
                arrStores = result.getArrStores();
                for (int i = 0; i < arrStores.size(); i++) {
                    arrStringStores.add(arrStores.get(i).getStorename());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                        R.layout.item_spinner_default, arrStringStores);
                adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
                spnStores.setAdapter(adapter);
            } else {
                if (result.getMessage() != null && !result.getMessage().isEmpty()) {
                    dialogManager.showCustomAlertDialog(getActivity(), getString(R.string.error),
                            result.getMessage(), true);
                } else {
                    dialogManager.showCustomAlertDialog(getActivity(), getString(R.string.error), getString(R.string.something_wrong2), true);
                }
            }
        } else if (object != null && object instanceof JSON_ProductsResult) {
            JSON_ProductsResult result = (JSON_ProductsResult) object;
            if (result.getArrProducts() != null && result.getArrProducts().size() > 0) {
                arrProducts = result.getArrProducts();

                ProductSalesTrackingAdapter adapter = new ProductSalesTrackingAdapter(getActivity(), arrProducts);
                lvProductInfo.setAdapter(adapter);
            }
        }
    }
}