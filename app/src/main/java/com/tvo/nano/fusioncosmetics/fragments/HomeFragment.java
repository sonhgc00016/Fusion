package com.tvo.nano.fusioncosmetics.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tvo.nano.fusioncosmetics.Constants;
import com.tvo.nano.fusioncosmetics.activities.DutyRosterActivity;
import com.tvo.nano.fusioncosmetics.activities.FrameContentActivity;
import com.tvo.nano.fusioncosmetics.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseAppFragment implements View.OnClickListener {

    private String userid, sessionid, timestamp, name;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //set staying page
        Constants.stayPage = Constants.StayPage.HOME;

        //set Actionbar values
        setHeadingPage(R.string.home);
        setImageLeftActionbarBackground(R.drawable.actionbar_imgempty);
        setImageRightActionbarBackground(R.drawable.actionbar_imglogout);
        setItemSoldCountVisible(false);

        //get user's infomation
        FrameContentActivity frameContentActivity = (FrameContentActivity) getActivity();
        sessionid = frameContentActivity.getSessionid();
        timestamp = frameContentActivity.getTimestamp();
        name = frameContentActivity.getName();
        userid = frameContentActivity.getUserid();
        name = frameContentActivity.getName();

        //set name for tvUser
        TextView tvUser = (TextView) view.findViewById(R.id.home_tvUser);
        tvUser.setText(name);

        ImageView imgMyProfile = (ImageView) view.findViewById(R.id.home_imgMyProfile);
        imgMyProfile.setOnClickListener(this);
        ImageView imgDutyRoster = (ImageView) view.findViewById(R.id.home_imgDutyRoster);
        imgDutyRoster.setOnClickListener(this);
        ImageView imgSalesTracking = (ImageView) view.findViewById(R.id.home_imgSalesTracking);
        imgSalesTracking.setOnClickListener(this);
        ImageView imgReports = (ImageView) view.findViewById(R.id.home_imgReports);
        imgReports.setOnClickListener(this);
        ImageView imgMembership = (ImageView) view.findViewById(R.id.home_imgMembership);
        imgMembership.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.home_imgMyProfile:
                replaceFragment(new MyProfileFragment(), true);
                break;
            case R.id.home_imgDutyRoster:
                Intent intent = new Intent(getActivity(), DutyRosterActivity.class);
                intent.putExtra(Constants.KEY_EXTRA_USERID, userid);
                intent.putExtra(Constants.KEY_EXTRA_SESSIONID, sessionid);
                intent.putExtra(Constants.KEY_EXTRA_TIME_STAMP, timestamp);
                startActivity(intent);
                break;
            case R.id.home_imgSalesTracking:
                replaceFragment(new SalesTrackingFragment(), true);
                break;
            case R.id.home_imgReports:
                replaceFragment(new ReportFragment(), true);
                break;
            case R.id.home_imgMembership:
                replaceFragment(new MembershipFragment(), true);
                break;
            default:
                break;
        }
    }
}
