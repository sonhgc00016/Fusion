package com.tvo.nano.fusioncosmetics.fragments;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.tvo.nano.fusioncosmetics.Constants;
import com.tvo.nano.fusioncosmetics.activities.FrameContentActivity;
import com.tvo.nano.fusioncosmetics.R;
import com.tvo.nano.json.JSON_StarsLoginResult;
import com.tvo.nano.network.CustomAlertDialogManager;
import com.tvo.nano.network.RequestAPI;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class LoginFragment extends BaseAppFragment implements RequestAPI.IResultApiListener {

    private EditText edtUsername, edtPassword;

    private String userid, name, user_id_number, sessionid,
            timestamp, dob, workinghours, offday, role, teammanager, contact, password;

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

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //set staying page
        Constants.stayPage = Constants.StayPage.LOGIN;

        //set Actionbar values
        setHeadingPage(R.string.login);
        setImageLeftActionbarBackground(R.drawable.actionbar_imgempty);
        setImageRightActionbarBackground(R.drawable.actionbar_imgempty);
        setItemSoldCountVisible(false);

        edtUsername = (EditText) view.findViewById(R.id.edtUsername);
        FrameContentActivity frameContentActivity = (FrameContentActivity) getActivity();
        //logout keep userid and and clear password
        if (frameContentActivity.getUserid() != null) {
            edtUsername.setText(frameContentActivity.getUserid());
        }
        edtPassword = (EditText) view.findViewById(R.id.edtPassword);
        ImageView imgSignin = (ImageView) view.findViewById(R.id.imgSignin);
        imgSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edtPassword.getWindowToken(), 0);
            }
        });
    }

    private void login() {
        final String URL = Constants.SERVER_URL + "StarsLogin";

        String devicetoken = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String timestamp = simpleDateFormat.format(new Date());

        Map<String, String> params = new HashMap<>();
        params.put("userid", edtUsername.getText().toString());
        params.put("password", edtPassword.getText().toString());
        params.put("devicetoken", devicetoken);
        params.put("timestamp", String.valueOf(timestamp));
        params.put("applicationkey", getActivity().getString(R.string.application_key));

        RequestAPI.requestAPI(getActivity(), URL, params, Request.Method.POST,
                JSON_StarsLoginResult.class, this, 2, "StarsLoginResult");
    }

    @Override
    public void onResult(Object object) {
        if (object != null) {
            JSON_StarsLoginResult result = (JSON_StarsLoginResult) object;
            if (result.getStatus().equals("1")) {//username or password invalid
                CustomAlertDialogManager dialogManager = new CustomAlertDialogManager();
                if (result.getMessage() != null && !result.getMessage().isEmpty()) {
                    dialogManager.showCustomAlertDialog(getActivity(), getString(R.string.error), result.getMessage(), true);
                } else {
                    dialogManager.showCustomAlertDialog(getActivity(), getString(R.string.error), getString(R.string.something_wrong2), true);
                }
            } else if(result.getStatus().equals("0")){//login successfull
                //redirect to HomePage and put values extra
                sessionid = String.valueOf(result.getSessionid());
                timestamp = String.valueOf(result.getTimestamp());
                name = String.valueOf(result.getBaDetails().getFullname());
                userid = String.valueOf(result.getBaDetails().getUserid());
                dob = String.valueOf(result.getBaDetails().getDob());
                workinghours = String.valueOf(result.getBaDetails().getWork_hrs_week());
                offday = String.valueOf(result.getBaDetails().getOffday());
                role = String.valueOf(result.getBaDetails().getRole());
                teammanager = String.valueOf(result.getBaDetails().getManager_team());
                contact = String.valueOf(result.getBaDetails().getMobilenumber());
                password = edtPassword.getText().toString();
                user_id_number = String.valueOf(result.getBaDetails().getUser_id_number());

                //remove all in backstack before redirect to Home Fragment
                //fix bug overlap layout login and home
                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.content_frame);
                if (fragment != null)
                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();

                //redirect to Home Page
                replaceFragment(new HomeFragment(), true);
            }
        }
    }
}
