package com.tvo.nano.fusioncosmetics.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.tvo.nano.adapters.CurrentMonthAdapter;
import com.tvo.nano.adapters.PreviousMonthAdapter;
import com.tvo.nano.adapters.PromotionAdapter;
import com.tvo.nano.fusioncosmetics.Constants;
import com.tvo.nano.fusioncosmetics.activities.FrameContentActivity;
import com.tvo.nano.fusioncosmetics.R;
import com.tvo.nano.json.JSON_ActivityPromo;
import com.tvo.nano.json.JSON_SalesReport;
import com.tvo.nano.json.JSON_SalesReportResult;
import com.tvo.nano.models.CurrentMonthData;
import com.tvo.nano.models.PreviousMonthData;
import com.tvo.nano.network.CustomAlertDialogManager;
import com.tvo.nano.network.RequestAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends BaseAppFragment implements RequestAPI.IResultApiListener, View.OnClickListener {

    private String sessionid, timestamp, userid;

    private TextView tvPromotion, tvPreviousMonth, tvCurrentMonth, tvNoDataPromotion, tvNoDataPreviousMonth, tvNoDataCurrentMonth;
    private ImageView imgUpDownPromotion, imgUpDownPreviousMonth, imgUpDownCurrentMonth;

    private ListView lvPromotion, lvPreviousMonth, lvCurrentMonth;

    private RelativeLayout rltPreviousMonthData, rltCurrentMonthData, rltPromotionData;
    private LinearLayout lnlTitlePreviousMonth, lnlTitleCurrentMonth;

    public ReportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //set statying page
        Constants.stayPage = Constants.StayPage.REPORT;

        //set Actionbar values
        setHeadingPage(R.string.report);
        setImageRightActionbarBackground(R.drawable.actionbar_imgempty);
        setImageLeftActionbarBackground(R.drawable.actionbar_imgback);

        //promotion
        rltPromotionData = (RelativeLayout) view.findViewById(R.id.report_rltPromotionData);
        tvPromotion = (TextView) view.findViewById(R.id.report_tvPromotion);
        tvPromotion.setOnClickListener(this);
        imgUpDownPromotion = (ImageView) view.findViewById(R.id.report_imgUpDownPromotion);
        imgUpDownPromotion.setOnClickListener(this);
        tvNoDataPromotion = (TextView) view.findViewById(R.id.report_tvNodataPromotion);
        lvPromotion = (ListView) view.findViewById(R.id.report_lvPromotion);

        //previous month
        rltPreviousMonthData = (RelativeLayout) view.findViewById(R.id.report_rltPreviousMonthData);
        lnlTitlePreviousMonth = (LinearLayout) view.findViewById(R.id.report_lnlTitlePreviousMonth);
        tvPreviousMonth = (TextView) view.findViewById(R.id.report_tvPreviousMonth);
        tvPreviousMonth.setOnClickListener(this);
        imgUpDownPreviousMonth = (ImageView) view.findViewById(R.id.report_imgUpDownPreviousMonth);
        imgUpDownPreviousMonth.setOnClickListener(this);
        tvNoDataPreviousMonth = (TextView) view.findViewById(R.id.report_tvNodataPreviousMonth);
        lvPreviousMonth = (ListView) view.findViewById(R.id.report_lvPreviousMonth);


        //current month
        rltCurrentMonthData = (RelativeLayout) view.findViewById(R.id.report_rltCurrentMonthData);
        lnlTitleCurrentMonth = (LinearLayout) view.findViewById(R.id.report_lnlTitleCurrentMonth);
        tvCurrentMonth = (TextView) view.findViewById(R.id.report_tvCurrentMonth);
        tvCurrentMonth.setOnClickListener(this);
        imgUpDownCurrentMonth = (ImageView) view.findViewById(R.id.report_imgUpDownCurrentMonth);
        imgUpDownCurrentMonth.setOnClickListener(this);
        tvNoDataCurrentMonth = (TextView) view.findViewById(R.id.report_tvNodataCurrentMonth);
        lvCurrentMonth = (ListView) view.findViewById(R.id.report_lvCurrentMonth);

        FrameContentActivity frameContentActivity = (FrameContentActivity) getActivity();
        sessionid = frameContentActivity.getSessionid();
        timestamp = frameContentActivity.getTimestamp();
        userid = frameContentActivity.getUserid();

        getSalesReport(userid, sessionid, timestamp);

    }

    private void getSalesReport(String userid, String sessionid, String timestamp) {
        final String URL = Constants.SERVER_URL + "GetSalesReport";

        Map<String, String> params = new HashMap<>();
        params.put("userid", userid);
        params.put("sessionid", sessionid);
        params.put("timestamp", timestamp);

        RequestAPI.requestAPI(getActivity(), URL, params, Request.Method.POST,
                JSON_SalesReportResult.class, this, 2, "GetSalesReportResult");
    }

    @Override
    public void onResult(Object object) {
        if (object != null && object instanceof JSON_SalesReportResult) {
            JSON_SalesReportResult result = (JSON_SalesReportResult) object;
            if (result.getSessionid() != null) {
                //getPromotion
                if (result.getArrActivityPromo() != null && result.getArrActivityPromo().size() > 0) {
                    ArrayList<JSON_ActivityPromo> arrActivityPromo = result.getArrActivityPromo();
                    PromotionAdapter adapterPromotion = new PromotionAdapter(getActivity(), arrActivityPromo);
                    lvPromotion.setAdapter(adapterPromotion);
                    tvNoDataPromotion.setVisibility(View.GONE);
                }

                //getSalesReport
                ArrayList<JSON_SalesReport> arrSalesReport = result.getArrSalesreport();

                if (arrSalesReport.get(0) != null) {
                    //getPreviousMonthData
                    ArrayList<PreviousMonthData> arrPreviousMonthData = arrSalesReport.get(0).getArrPreviousMonthData();
                    if (arrPreviousMonthData != null && arrPreviousMonthData.size() > 0) {
                        PreviousMonthAdapter adapterPreviousMonth = new PreviousMonthAdapter(getActivity(), arrPreviousMonthData);
                        lvPreviousMonth.setAdapter(adapterPreviousMonth);
                        tvNoDataPreviousMonth.setVisibility(View.GONE);
                    } else {
                        lnlTitlePreviousMonth.setVisibility(View.GONE);
                    }

                    //getCurrentMonthData
                    ArrayList<CurrentMonthData> arrCurrentMonthData = arrSalesReport.get(0).getArrCurrentMonthData();
                    if (arrCurrentMonthData != null && arrCurrentMonthData.size() > 0) {
                        CurrentMonthAdapter adapterCurrentMonth = new CurrentMonthAdapter(getActivity(), arrCurrentMonthData);
                        lvCurrentMonth.setAdapter(adapterCurrentMonth);
                        tvNoDataCurrentMonth.setVisibility(View.GONE);
                    } else {
                        lnlTitleCurrentMonth.setVisibility(View.GONE);
                    }
                }
            } else {
                CustomAlertDialogManager dialogManager = new CustomAlertDialogManager();
                dialogManager.showCustomAlertDialog(getActivity(), getResources().getString(R.string.error),
                        getResources().getString(R.string.invalid_session) + "."
                                + getResources().getString(R.string.login_again), true);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.report_tvPromotion:
                expandCollapsePromotion();
                break;
            case R.id.report_imgUpDownPromotion:
                expandCollapsePromotion();
                break;
            case R.id.report_tvPreviousMonth:
                expandCollapsePreviousMonth();
                break;
            case R.id.report_imgUpDownPreviousMonth:
                expandCollapsePreviousMonth();
                break;
            case R.id.report_tvCurrentMonth:
                expandCollapseCurrentMonth();
                break;
            case R.id.report_imgUpDownCurrentMonth:
                expandCollapseCurrentMonth();
                break;
            default:
                break;
        }
    }

    private boolean isPromotionCollapsed = true;

    private void expandCollapsePromotion() {
        if (isPromotionCollapsed) {
            imgUpDownPromotion.setBackgroundResource(R.drawable.report_imgup_arrow);
            rltPromotionData.setVisibility(View.VISIBLE);
            isPromotionCollapsed = false;
        } else {
            imgUpDownPromotion.setBackgroundResource(R.drawable.report_imgdown_arrow);
            rltPromotionData.setVisibility(View.GONE);
            isPromotionCollapsed = true;
        }
    }

    private boolean isPreviousMonthCollapsed = true;

    private void expandCollapsePreviousMonth() {
        if (isPreviousMonthCollapsed) {
            imgUpDownPreviousMonth.setBackgroundResource(R.drawable.report_imgup_arrow);
            rltPreviousMonthData.setVisibility(View.VISIBLE);
            isPreviousMonthCollapsed = false;
        } else {
            imgUpDownPreviousMonth.setBackgroundResource(R.drawable.report_imgdown_arrow);
            rltPreviousMonthData.setVisibility(View.GONE);
            isPreviousMonthCollapsed = true;
        }
    }

    private boolean isCurrentMonthCollapsed = true;

    private void expandCollapseCurrentMonth() {
        if (isCurrentMonthCollapsed) {
            imgUpDownCurrentMonth.setBackgroundResource(R.drawable.report_imgup_arrow);
            rltCurrentMonthData.setVisibility(View.VISIBLE);
            isCurrentMonthCollapsed = false;
        } else {
            imgUpDownCurrentMonth.setBackgroundResource(R.drawable.report_imgdown_arrow);
            rltCurrentMonthData.setVisibility(View.GONE);
            isCurrentMonthCollapsed = true;
        }
    }
}
