package com.tvo.nano.fusioncosmetics.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
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
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class MembershipUpdateProfileFragment extends BaseAppFragment implements RequestAPI.IResultApiListener, View.OnClickListener {

    private String sessionid, timestamp, userid;
    private String customerId, salutation, fullname, gender, nric_fin, dob,
            nationalityId, raceId, email, address, postal_code, handphone, home_office, status;

    private Spinner spnSalutation, spnNationality, spnRace;
    private RadioButton rbtnFemale, rbtnMale, rbtnSingle, rbtnMarried, rbtnWithKids;
    private ArrayList<Nationality> arrNationalities = new ArrayList<>();
    private ArrayList<Race> arrRaces = new ArrayList<>();
    private EditText edtFullname, edtNricFin, edtDob, edtEmail, edtAddress, edtPostalCode, edtHandphone, edtHomeOffice;
    private CheckBox cbTerms;
    private TextView tvLinkTerm;
    private ImageView btnSubmit, imgCalendar;
    private DatePickerDialog picker;
    private JSON_RegisterCustomer updateCustomer = new JSON_RegisterCustomer();
    private CustomAlertDialogManager dialogManager = new CustomAlertDialogManager();
    private FrameContentActivity frameContentActivity;

    public JSON_RegisterCustomer getUpdateCustomer() {
        return updateCustomer;
    }

    public MembershipUpdateProfileFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(String customerId, String salutation, String fullname, String gender, String nric_fin,
                                       String dob, String nationalityId, String raceId, String email, String address,
                                       String postal_code, String handphone, String home_office, String status) {
        Fragment f = new MembershipUpdateProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_EXTRA_CUSTOMER_ID, customerId);
        bundle.putString(Constants.KEY_EXTRA_SALUTATION, salutation);
        bundle.putString(Constants.KEY_EXTRA_FULL_NAME, fullname);
        bundle.putString(Constants.KEY_EXTRA_GENDER, gender);
        bundle.putString(Constants.KEY_EXTRA_NRIC_FIN, nric_fin);
        bundle.putString(Constants.KEY_EXTRA_DOB, dob);
        bundle.putString(Constants.KEY_EXTRA_NATIONALITY_ID, nationalityId);
        bundle.putString(Constants.KEY_EXTRA_RACE_ID, raceId);
        bundle.putString(Constants.KEY_EXTRA_EMAIL, email);
        bundle.putString(Constants.KEY_EXTRA_ADDRESS, address);
        bundle.putString(Constants.KEY_EXTRA_POSTAL_CODE, postal_code);
        bundle.putString(Constants.KEY_EXTRA_HANDPHONE, handphone);
        bundle.putString(Constants.KEY_EXTRA_HOME_OFFICE, home_office);
        bundle.putString(Constants.KEY_EXTRA_STATUS, status);
        f.setArguments(bundle);
        return f;
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
        Constants.stayPage = Constants.StayPage.MEMBERSHIP_PROFILE_UPDATE;

        //set Actionbar values
        setHeadingPage(R.string.membership_profile_update);
        setImageLeftActionbarBackground(R.drawable.actionbar_imgback);
        setImageRightActionbarBackground(R.drawable.actionbar_imgempty);
        setItemSoldCountVisible(false);

        //get user's infomation
        frameContentActivity = (FrameContentActivity) getActivity();
        userid = frameContentActivity.getUserid();
        sessionid = frameContentActivity.getSessionid();
        timestamp = frameContentActivity.getTimestamp();

        arrNationalities.addAll(frameContentActivity.getArrNationalities());
        arrRaces.addAll(frameContentActivity.getArrRaces());

        //get Value extra
        Bundle bundle = getArguments();
        customerId = bundle.getString(Constants.KEY_EXTRA_CUSTOMER_ID, "");
        salutation = bundle.getString(Constants.KEY_EXTRA_SALUTATION, "");
        fullname = bundle.getString(Constants.KEY_EXTRA_FULL_NAME, "");
        gender = bundle.getString(Constants.KEY_EXTRA_GENDER, "");
        nric_fin = bundle.getString(Constants.KEY_EXTRA_NRIC_FIN, "");
        dob = bundle.getString(Constants.KEY_EXTRA_DOB, "");
        nationalityId = bundle.getString(Constants.KEY_EXTRA_NATIONALITY_ID, "");
        raceId = bundle.getString(Constants.KEY_EXTRA_RACE_ID, "");
        email = bundle.getString(Constants.KEY_EXTRA_EMAIL, "");
        address = bundle.getString(Constants.KEY_EXTRA_ADDRESS, "");
        postal_code = bundle.getString(Constants.KEY_EXTRA_POSTAL_CODE, "");
        handphone = bundle.getString(Constants.KEY_EXTRA_HANDPHONE, "");
        home_office = bundle.getString(Constants.KEY_EXTRA_HOME_OFFICE, "");
        status = bundle.getString(Constants.KEY_EXTRA_STATUS, "");

        edtFullname = (EditText) view.findViewById(R.id.membership_edtFullName);
        edtFullname.setText(fullname);
        edtNricFin = (EditText) view.findViewById(R.id.membership_edtNricFin);
        edtNricFin.setText(nric_fin);

        imgCalendar = (ImageView) view.findViewById(R.id.membership_imgCalendar);

        //set current Date to edtDate
        final SimpleDateFormat dateFormatter = new SimpleDateFormat(getString(R.string.date_format), Locale.ROOT);
        Calendar calendar = Calendar.getInstance();
        String dateInString = dob;
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
        edtEmail.setText(email);
        edtAddress = (EditText) view.findViewById(R.id.membership_edtAddress);
        edtAddress.setText(address);
        edtPostalCode = (EditText) view.findViewById(R.id.membership_edtPostalCode);
        edtPostalCode.setText(postal_code);
        edtHandphone = (EditText) view.findViewById(R.id.membership_edtHandphone);
        edtHandphone.setText(handphone);
        edtHomeOffice = (EditText) view.findViewById(R.id.membership_edtHomeOffice);
        edtHomeOffice.setText(home_office);

        spnSalutation = (Spinner) view.findViewById(R.id.membership_spnSalutation);
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
        spnSalutation.setSelection(arrStringSalutation.indexOf(salutation));

        spnNationality = (Spinner) view.findViewById(R.id.membership_spnNationality);
        ArrayList<String> arrStringNationality = new ArrayList<>();
        arrStringNationality.add("Please Select");
        for (Nationality nationality : arrNationalities) {
            arrStringNationality.add(nationality.getNationality_name());
        }
        ArrayAdapter<String> adapterNationality = new ArrayAdapter<>(getActivity(),
                R.layout.item_spinner_default, arrStringNationality);
        adapterNationality.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spnNationality.setAdapter(adapterNationality);
        for (Nationality nationality : arrNationalities) {
            if (nationality.getNationality_id().equals(nationalityId)) {
                spnNationality.setSelection(arrStringNationality.indexOf(nationality.getNationality_name()));
            }
        }

        spnRace = (Spinner) view.findViewById(R.id.membership_spnRace);
        ArrayList<String> arrStringRace = new ArrayList<>();
        arrStringRace.add("Please Select");
        for (Race race : arrRaces) {
            arrStringRace.add(race.getRace_name());
        }
        ArrayAdapter<String> adapterRace = new ArrayAdapter<>(getActivity(),
                R.layout.item_spinner_default, arrStringRace);
        adapterRace.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spnRace.setAdapter(adapterRace);
        for (Race race : arrRaces) {
            if (race.getRace_id().equals(raceId)) {
                spnRace.setSelection(arrStringRace.indexOf(race.getRace_name()));
            }
        }

        cbTerms = (CheckBox) view.findViewById(R.id.membership_cbTerm);
        cbTerms.setChecked(true);

        tvLinkTerm = (TextView) view.findViewById(R.id.membership_tvLinkTerm);
        tvLinkTerm.setOnClickListener(this);

        btnSubmit = (ImageView) view.findViewById(R.id.membership_imgSubmit);
        btnSubmit.setOnClickListener(this);

        rbtnFemale = (RadioButton) view.findViewById(R.id.membership_rbtnFemale);
        rbtnMale = (RadioButton) view.findViewById(R.id.membership_rbtnMale);
        if (gender.equals(rbtnFemale.getText().toString().trim())) {
            rbtnFemale.setChecked(true);
        } else {
            rbtnMale.setChecked(true);
        }

        rbtnSingle = (RadioButton) view.findViewById(R.id.membership_rbtnSingle);
        rbtnMarried = (RadioButton) view.findViewById(R.id.membership_rbtnMarried);
        rbtnWithKids = (RadioButton) view.findViewById(R.id.membership_rbtnWithKids);
        if (status.equals("1")) {
            rbtnSingle.setChecked(true);
        } else if (status.equals("2")) {
            rbtnMarried.setChecked(true);
        } else if (status.equals("3")) {
            rbtnWithKids.setChecked(true);
        }

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
    }

    private void updateCustomer() {
        updateCustomer.setUserid(userid);
        updateCustomer.setSessionid(sessionid);
        updateCustomer.setTimestamp(timestamp);
        Customer customer = new Customer();
        customer.setCustomer_id(customerId);
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
        updateCustomer.setCustomerprofile(customer);

        final String URL = Constants.SERVER_URL + "UpdateCustomer";

        RequestAPI.requestAPI(getActivity(), URL, null, Request.Method.POST,
                JSON_RegisterCustomerResult.class, this, 2, "UpdateCustomerResult");
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

    @Override
    public void onResult(Object object) {
        if (object != null && object instanceof JSON_RegisterCustomerResult) {
            JSON_RegisterCustomerResult result = (JSON_RegisterCustomerResult) object;
            if (result.getStatus().equals("0")) {
                dialogManager.showCustomAlertDialog(getActivity(), getString(R.string.info), "Updated successfully.", false);
                //Fix bug search->edit->submit->back cannot back to previous page
                Fragment fragment = frameContentActivity.getSupportFragmentManager().findFragmentById(R.id.content_frame);
                if (fragment != null)
                    frameContentActivity.getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                frameContentActivity.getSupportFragmentManager().popBackStack();
                // end fix
                replaceFragment(new SearchFragment(), false);
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
                    updateCustomer();
                }
                break;
            default:
                break;
        }
    }
}
