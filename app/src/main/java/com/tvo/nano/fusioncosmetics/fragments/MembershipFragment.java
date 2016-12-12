package com.tvo.nano.fusioncosmetics.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.tvo.nano.fusioncosmetics.Constants;
import com.tvo.nano.fusioncosmetics.R;
import com.tvo.nano.fusioncosmetics.activities.FrameContentActivity;
import com.tvo.nano.json.JSON_NationalityResult;
import com.tvo.nano.json.JSON_RaceResult;
import com.tvo.nano.json.JSON_RegisterCustomer;
import com.tvo.nano.json.JSON_RegisterCustomerResult;
import com.tvo.nano.models.Customer;
import com.tvo.nano.models.Nationality;
import com.tvo.nano.models.Race;
import com.tvo.nano.network.CustomAlertDialogManager;
import com.tvo.nano.network.RequestAPI;

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
public class MembershipFragment extends BaseAppFragment implements RequestAPI.IResultApiListener, View.OnClickListener, FrameContentActivity.OnBackPressedListener {

    private Spinner spnSalutation, spnNationality, spnRace;
    private RadioButton rbtnFemale, rbtnMale, rbtnSingle, rbtnMarried, rbtnWithKids;
    private ArrayList<Nationality> arrNationalities = new ArrayList<>();
    private ArrayList<Race> arrRaces = new ArrayList<>();
    private EditText edtFullname, edtNricFin, edtDob, edtEmail, edtAddress, edtPostalCode, edtHandphone, edtHomeOffice;
    private CheckBox cbTerms;
    private ImageView btnSubmit, imgCalendar;
    private DatePickerDialog picker;
    private TextView tvLinkTerm;
    private FrameContentActivity frameContentActivity;

    private JSON_RegisterCustomer registerCus = new JSON_RegisterCustomer();

    public JSON_RegisterCustomer getRegisterCus() {
        return registerCus;
    }

    private String sessionid, timestamp, userid;

    private CustomAlertDialogManager dialogManager = new CustomAlertDialogManager();

    public ArrayList<Nationality> getArrNationalities() {
        return arrNationalities;
    }

    public ArrayList<Race> getArrRaces() {
        return arrRaces;
    }

    public MembershipFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_membership, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //set staying page
        Constants.stayPage = Constants.StayPage.MEMBERSHIP;

        //set Actionbar values
        setHeadingPage(R.string.membership);
        setImageLeftActionbarBackground(R.drawable.actionbar_imgback);
        setImageRightActionbarBackground(R.drawable.actionbar_imgsearch);
        setItemSoldCountVisible(false);

        //get user's infomation
        frameContentActivity = (FrameContentActivity) getActivity();
        frameContentActivity.setOnBackPressedListener(this);
        userid = frameContentActivity.getUserid();
        sessionid = frameContentActivity.getSessionid();
        timestamp = frameContentActivity.getTimestamp();

        edtFullname = (EditText) view.findViewById(R.id.membership_edtFullName);
        edtNricFin = (EditText) view.findViewById(R.id.membership_edtNricFin);
        edtDob = (EditText) view.findViewById(R.id.membership_edtDob);

        imgCalendar = (ImageView) view.findViewById(R.id.membership_imgCalendar);

        //set Date to edtDate
        final SimpleDateFormat dateFormatter = new SimpleDateFormat(getString(R.string.date_format), Locale.ROOT);
        Calendar calendar = Calendar.getInstance();
        String dateInString = "01-01-1980";
        try {
            Date date = dateFormatter.parse(dateInString);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        edtDob = (EditText) view.findViewById(R.id.membership_edtDob);
        edtDob.setText(dateFormatter.format(calendar.getTime()));

        imgCalendar.setOnClickListener(this);
        //catch event DatePickerDialog onDataSet
        picker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newCalendar = Calendar.getInstance();
                newCalendar.set(year, monthOfYear, dayOfMonth);
                edtDob.setText(dateFormatter.format(newCalendar.getTime()));
            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        edtEmail = (EditText) view.findViewById(R.id.membership_edtEmail);
        edtAddress = (EditText) view.findViewById(R.id.membership_edtAddress);
        edtPostalCode = (EditText) view.findViewById(R.id.membership_edtPostalCode);
        edtHandphone = (EditText) view.findViewById(R.id.membership_edtHandphone);
        edtHomeOffice = (EditText) view.findViewById(R.id.membership_edtHomeOffice);

        spnSalutation = (Spinner) view.findViewById(R.id.membership_spnSalutation);
        spnNationality = (Spinner) view.findViewById(R.id.membership_spnNationality);
        spnRace = (Spinner) view.findViewById(R.id.membership_spnRace);

        cbTerms = (CheckBox) view.findViewById(R.id.membership_cbTerm);

        btnSubmit = (ImageView) view.findViewById(R.id.membership_imgSubmit);
        btnSubmit.setOnClickListener(this);

        tvLinkTerm = (TextView) view.findViewById(R.id.membership_tvLinkTerm);
        tvLinkTerm.setOnClickListener(this);

        ArrayList<String> arrStringSalutation = new ArrayList<>();
        arrStringSalutation.add("Mr.");
        arrStringSalutation.add("Mrs.");
        arrStringSalutation.add("Miss");
        arrStringSalutation.add("Mdm");
        arrStringSalutation.add("Dr.");

        ArrayAdapter<String> adapterSalutation = new ArrayAdapter<>(getActivity(),
                R.layout.item_spinner_default, arrStringSalutation);
        adapterSalutation.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spnSalutation.setAdapter(adapterSalutation);

        rbtnFemale = (RadioButton) view.findViewById(R.id.membership_rbtnFemale);
        rbtnMale = (RadioButton) view.findViewById(R.id.membership_rbtnMale);
        rbtnSingle = (RadioButton) view.findViewById(R.id.membership_rbtnSingle);
        rbtnMarried = (RadioButton) view.findViewById(R.id.membership_rbtnMarried);
        rbtnWithKids = (RadioButton) view.findViewById(R.id.membership_rbtnWithKids);
        rbtnSingle.setChecked(true);

        // custom check like view group for 3 radio button rbtnSingle, rbtnMarried, rbtnWithKids
        rbtnSingle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rbtnMarried.setChecked(false);
                    rbtnWithKids.setChecked(false);
                }
            }
        });

        rbtnMarried.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rbtnSingle.setChecked(false);
                    rbtnWithKids.setChecked(false);
                }
            }
        });

        rbtnWithKids.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rbtnSingle.setChecked(false);
                    rbtnMarried.setChecked(false);
                }
            }
        });

        getNationalityResult(userid, sessionid, timestamp);
        getRaceResult(userid, sessionid, timestamp);
    }

    private void registerCustomer() {
        registerCus.setUserid(userid);
        registerCus.setSessionid(sessionid);
        registerCus.setTimestamp(timestamp);
        Customer customer = new Customer();
        customer.setSalutation(spnSalutation.getSelectedItem().toString());
        customer.setFullname(edtFullname.getText().toString().trim());
        if (rbtnFemale.isChecked()) {
            customer.setGender(rbtnFemale.getText().toString().trim());
        } else {
            customer.setGender(rbtnMale.getText().toString().trim());
        }
        customer.setNric_pass_number(edtNricFin.getText().toString().trim());
        customer.setDateofbirth(edtDob.getText().toString().trim());
        if (spnNationality.getSelectedItemPosition() >= 1)
            customer.setNationality_id(arrNationalities.get(spnNationality.getSelectedItemPosition() - 1).getNationality_id());
        if (spnRace.getSelectedItemPosition() >= 1)
            customer.setRace_id(arrRaces.get(spnRace.getSelectedItemPosition() - 1).getRace_id());
        customer.setEmail(edtEmail.getText().toString().trim());
        customer.setAddress(edtAddress.getText().toString().trim());
        customer.setPostalcode(edtPostalCode.getText().toString().trim());
        customer.setHandphone(edtHandphone.getText().toString().trim());
        customer.setHomephone(edtHomeOffice.getText().toString().trim());
        if (rbtnSingle.isChecked()) {
            customer.setMaritalstatus("1");
        } else if (rbtnMarried.isChecked()) {
            customer.setMaritalstatus("2");
        } else {
            customer.setMaritalstatus("3");
        }
        if (cbTerms.isChecked()) {
            customer.setIs_acceptance("1");
        } else {
            customer.setIs_acceptance("0");
        }
        registerCus.setCustomerprofile(customer);

        final String URL = Constants.SERVER_URL + "RegisterCustomer";

        RequestAPI.requestAPI(getActivity(), URL, null, Request.Method.POST,
                JSON_RegisterCustomerResult.class, this, 2, "RegisterCustomerResult");
    }

    private boolean validate() {
        String pattern_ic_passport = getString(R.string.regex_ic_passport);
        String pattern_email = getString(R.string.regex_email);
        String pattern_phone_no = getString(R.string.regex_phone_no);
        if (edtFullname.getText().toString().trim().isEmpty()) {
            dialogManager.showCustomAlertDialog(getActivity(), getString(R.string.error), "Full Name cannot empty.", true);
        } else if (edtNricFin.getText().toString().trim().isEmpty()) {
            dialogManager.showCustomAlertDialog(getActivity(), getString(R.string.error), "NRIC/FIN cannot empty.", true);
        } else if (!edtNricFin.getText().toString().trim().matches(pattern_ic_passport)) {
            dialogManager.showCustomAlertDialog(getActivity(), getString(R.string.error), "NRIC/FIN allow number and alphabet only.", true);
        } else if (edtNricFin.getText().toString().trim().length() != 9) {
            dialogManager.showCustomAlertDialog(getActivity(), getString(R.string.error), "NRIC/FIN field only allow 9 character.", true);
        } else if (edtEmail.getText().toString().trim().isEmpty()) {
            dialogManager.showCustomAlertDialog(getActivity(), getString(R.string.error), "Email cannot empty.", true);
        } else if (!edtEmail.getText().toString().trim().matches(pattern_email)) {
            dialogManager.showCustomAlertDialog(getActivity(), getString(R.string.error), "Invalid Email Address.", true);
        } else if (edtPostalCode.getText().toString().trim().isEmpty()) {
            dialogManager.showCustomAlertDialog(getActivity(), getString(R.string.error), "Postal Code cannot empty.", true);
        } else if (edtHandphone.getText().toString().trim().isEmpty()) {
            dialogManager.showCustomAlertDialog(getActivity(), getString(R.string.error), "Handphone cannot empty.", true);
        } else if ((!edtHandphone.getText().toString().trim().isEmpty()
                && !edtHandphone.getText().toString().matches(pattern_phone_no))) {
            dialogManager.showCustomAlertDialog(getActivity(), getString(R.string.error), "Contact No can only have 8 numbers & can only start with 8 or 9.", true);
        } else if (!cbTerms.isChecked()) {
            dialogManager.showCustomAlertDialog(getActivity(), getString(R.string.error), "You must agree to the Terms of Use!", true);
        } else {
            return true;
        }
        return false;
    }

    private void getNationalityResult(String userid, String sessionid, String timestamp) {
        final String URL = Constants.SERVER_URL + "GetNationality";

        Map<String, String> params = new HashMap<>();
        params.put("userid", userid);
        params.put("sessionid", sessionid);
        params.put("timestamp", timestamp);

        RequestAPI.requestAPI(getActivity(), URL, params, Request.Method.POST,
                JSON_NationalityResult.class, this, 1, "GetNationalityResult");
    }

    private void getRaceResult(String userid, String sessionid, String timestamp) {
        final String URL = Constants.SERVER_URL + "GetRace";

        Map<String, String> params = new HashMap<>();
        params.put("userid", userid);
        params.put("sessionid", sessionid);
        params.put("timestamp", timestamp);

        RequestAPI.requestAPI(getActivity(), URL, params, Request.Method.POST,
                JSON_RaceResult.class, this, 2, "GetRaceResult");
    }

    @Override
    public void onResult(Object object) {
        if (object != null && object instanceof JSON_NationalityResult) {
            JSON_NationalityResult result = (JSON_NationalityResult) object;
            ArrayList<String> arrStringNationalities = new ArrayList<>();
            arrStringNationalities.add("Please Select");
            if (result.getNationalities() != null && result.getNationalities().size() > 0) {
                arrNationalities = result.getNationalities();
                for (int i = 0; i < arrNationalities.size(); i++) {
                    arrStringNationalities.add(arrNationalities.get(i).getNationality_name());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                        R.layout.item_spinner_default, arrStringNationalities);
                adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
                spnNationality.setAdapter(adapter);
            }
        } else if (object != null && object instanceof JSON_RaceResult) {
            JSON_RaceResult result = (JSON_RaceResult) object;
            ArrayList<String> arrStringRaces = new ArrayList<>();
            arrStringRaces.add("Please select");
            if (result.getRaces() != null && result.getRaces().size() > 0) {
                arrRaces = result.getRaces();
                for (int i = 0; i < arrRaces.size(); i++) {
                    arrStringRaces.add(arrRaces.get(i).getRace_name());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                        R.layout.item_spinner_default, arrStringRaces);
                adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
                spnRace.setAdapter(adapter);
            }
        } else if (object != null && object instanceof JSON_RegisterCustomerResult) {
            JSON_RegisterCustomerResult result = (JSON_RegisterCustomerResult) object;
            if (result.getStatus().equals("0")) {
                replaceFragment(new HomeFragment(), false);
                dialogManager.showCustomAlertDialog(getActivity(), getString(R.string.info), "Register successfully.", false);
            } else if (result.getStatus().equals("1")) {
                if (result.getMessage() != null && !result.getMessage().isEmpty()) {
                    dialogManager.showCustomAlertDialog(getActivity(), getString(R.string.error), result.getMessage(), true);
                } else {
                    dialogManager.showCustomAlertDialog(getActivity(), getString(R.string.error), getString(R.string.something_wrong2), true);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.membership_imgCalendar:
                picker.show();
                break;
            case R.id.membership_tvLinkTerm:
                // custom dialog
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.custom_term_dialog);

                // set the custom dialog components - text, image and button
                TextView tvTitle = (TextView) dialog.findViewById(R.id.customDialog_tvTitle);
                tvTitle.setText("Terms and conditions");
                WebView webViewTerm = (WebView) dialog.findViewById(R.id.customDialog_webviewTerm);
                webViewTerm.getSettings().setSupportZoom(false);
                webViewTerm.setWebChromeClient(new WebChromeClient());
                webViewTerm.setWebViewClient(new WebViewClient());
                webViewTerm.getSettings().setJavaScriptEnabled(true);
                webViewTerm.getSettings().setAppCacheEnabled(true);
                webViewTerm.getSettings().setDomStorageEnabled(true);
                webViewTerm.setVerticalScrollBarEnabled(false);
                webViewTerm.setHorizontalScrollBarEnabled(false);
                webViewTerm.getSettings().setAllowFileAccess(true);
                webViewTerm.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                webViewTerm.getSettings().setSupportMultipleWindows(true);
                webViewTerm.getSettings().setPluginState(WebSettings.PluginState.ON);
                webViewTerm.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
                webViewTerm.loadUrl("file:///android_asset/fusion_termofuse.html");
                ImageView imgOK = (ImageView) dialog.findViewById(R.id.customDialog_imgOk);
                imgOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            case R.id.membership_imgSubmit:
                if (validate()) {
                    registerCustomer();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void doBack() {
        Fragment fragment = frameContentActivity.getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (fragment != null)
            frameContentActivity.getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        frameContentActivity.getSupportFragmentManager().popBackStack();
    }
}
