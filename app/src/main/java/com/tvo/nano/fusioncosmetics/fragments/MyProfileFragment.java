package com.tvo.nano.fusioncosmetics.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tvo.nano.fusioncosmetics.Constants;
import com.tvo.nano.fusioncosmetics.activities.FrameContentActivity;
import com.tvo.nano.fusioncosmetics.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfileFragment extends BaseAppFragment implements FrameContentActivity.OnBackPressedListener {

    private String sessionid, timestamp, name, userid, dob, workinghours, offday, role, teammanager, contact, user_id_number;
    private ImageView imgChangePassword;
    private FrameContentActivity frameContentActivity;

    public MyProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //set statying page
        Constants.stayPage = Constants.StayPage.MY_PROFILE;

        //set Actionbar values
        setHeadingPage(R.string.my_profile);
        setImageRightActionbarBackground(R.drawable.actionbar_imgempty);
        setImageLeftActionbarBackground(R.drawable.actionbar_imgback);
        setItemSoldCountVisible(false);

        //get user's infomation
        frameContentActivity = (FrameContentActivity) getActivity();
        frameContentActivity.setOnBackPressedListener(this);
        sessionid = frameContentActivity.getSessionid();
        timestamp = frameContentActivity.getTimestamp();
        name = frameContentActivity.getName();
        userid = frameContentActivity.getUserid();
        dob = frameContentActivity.getDob();
        workinghours = frameContentActivity.getWorkinghours();
        offday = frameContentActivity.getOffday();
        role = frameContentActivity.getRole();
        teammanager = frameContentActivity.getTeammanager();
        contact = frameContentActivity.getContact();
        user_id_number = frameContentActivity.getUser_id_number();

        //set Text for view
        TextView tvFullName = (TextView) view.findViewById(R.id.myProfile_tvFullName);
        tvFullName.setText(name);
        TextView tvIdNumber = (TextView) view.findViewById(R.id.myProfile_tvIdNumber);
        tvIdNumber.setText(user_id_number);
        TextView tvDob = (TextView) view.findViewById(R.id.myProfile_tvDateOfBirth);
        tvDob.setText(dob);
        TextView tvWorkinghours = (TextView) view.findViewById(R.id.myProfile_tvWorkingHours);
        tvWorkinghours.setText(workinghours);
        TextView tvOffday = (TextView) view.findViewById(R.id.myProfile_tvOffDay);
        tvOffday.setText(offday);
        TextView tvRole = (TextView) view.findViewById(R.id.myProfile_tvRole);
        tvRole.setText(role);
        TextView tvTeammanager = (TextView) view.findViewById(R.id.myProfile_tvTeamManager);
        tvTeammanager.setText(teammanager);
        TextView tvContact = (TextView) view.findViewById(R.id.myProfile_tvContact);
        tvContact.setText(contact);

        imgChangePassword = (ImageView) view.findViewById(R.id.myProfile_imgChangePassword);
        imgChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ChangePasswordFragment(), true);
            }
        });
    }

    @Override
    public void doBack() {
        Fragment fragment = frameContentActivity.getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (fragment != null)
            frameContentActivity.getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        frameContentActivity.getSupportFragmentManager().popBackStack();
    }
}
