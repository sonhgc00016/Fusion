package com.tvo.nano.fusioncosmetics.fragments;


import android.content.Context;
import android.os.Bundle;
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
import com.tvo.nano.json.JSON_StarsChangePasswordResult;
import com.tvo.nano.network.CustomAlertDialogManager;
import com.tvo.nano.network.RequestAPI;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends BaseAppFragment implements RequestAPI.IResultApiListener {

    private String sessionid, timestamp, name, userid, dob, workinghours, offday, role, teammanager, contact, password;
    private EditText edtOldPassword, edtNewPassword, edtConfirmNewPassword;
    private ImageView imgSubmit;
    CustomAlertDialogManager dialogManager;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //set staying page
        Constants.stayPage = Constants.StayPage.CHANGE_PASSWORD;

        //set Actionbar values
        setHeadingPage(R.string.change_password);
        setImageRightActionbarBackground(R.drawable.actionbar_imgempty);
        setImageLeftActionbarBackground(R.drawable.actionbar_imgback);
        setItemSoldCountVisible(false);

        //get user's infomation
        FrameContentActivity frameContentActivity = (FrameContentActivity) getActivity();
        userid = frameContentActivity.getUserid();
        sessionid = frameContentActivity.getSessionid();
        timestamp = frameContentActivity.getTimestamp();
        password = frameContentActivity.getPassword();

        edtOldPassword = (EditText) view.findViewById(R.id.edtOldPassword);
        edtNewPassword = (EditText) view.findViewById(R.id.edtNewPassword);
        edtConfirmNewPassword = (EditText) view.findViewById(R.id.edtConfirmNewPassword);

        imgSubmit = (ImageView) view.findViewById(R.id.imgSubmit);
        imgSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = edtOldPassword.getText().toString();
                String newPassword = edtNewPassword.getText().toString();
                String confirmNewPassword = edtConfirmNewPassword.getText().toString();
                dialogManager = new CustomAlertDialogManager();
                if (oldPassword.isEmpty()) {
                    dialogManager.showCustomAlertDialog(getActivity(), getString(R.string.error),
                            getResources().getString(R.string.old_password_empty), true);
                } else if (newPassword.isEmpty()) {
                    dialogManager.showCustomAlertDialog(getActivity(), getString(R.string.error),
                            getResources().getString(R.string.new_password_empty), true);
                } else if (!confirmNewPassword.equals(newPassword)) {
                    dialogManager.showCustomAlertDialog(getActivity(), getString(R.string.error),
                            getResources().getString(R.string.validate_password), true);
                } else {
                    changePassword(userid, sessionid, timestamp, oldPassword, newPassword);
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edtConfirmNewPassword.getWindowToken(), 0);
                }
            }
        });
    }

    private void changePassword(String userid, String sessionid, String timestamp, String oldPassword, String newPassword) {
        final String URL = Constants.SERVER_URL + "StarsChangePassword";

        Map<String, String> params = new HashMap<>();
        params.put("userid", userid);
        params.put("sessionid", sessionid);
        params.put("timestamp", timestamp);
        params.put("oldpassword", oldPassword);
        params.put("newpassword", newPassword);

        RequestAPI.requestAPI(getActivity(), URL, params, Request.Method.POST,
                JSON_StarsChangePasswordResult.class, this, 2, "StarsChangePasswordResult");
    }

    @Override
    public void onResult(Object object) {
        if (object != null && object instanceof JSON_StarsChangePasswordResult) {
            JSON_StarsChangePasswordResult result = (JSON_StarsChangePasswordResult) object;
            dialogManager.showCustomAlertDialog(getActivity(), getString(R.string.info), String.valueOf(result.getMessage()), true);
            if (result.getStatus().equals("0")) {
                replaceFragment(new MyProfileFragment(), false);
            } else if (result.getStatus().equals("1")) {
                if (result.getMessage() != null && !result.getMessage().isEmpty()) {
                    dialogManager.showCustomAlertDialog(getActivity(), getString(R.string.error), result.getMessage(), true);
                } else {
                    dialogManager.showCustomAlertDialog(getActivity(), getString(R.string.error), getString(R.string.something_wrong2), true);
                }
                edtOldPassword.setText("");
                edtNewPassword.setText("");
                edtConfirmNewPassword.setText("");
            }
        }
    }
}
