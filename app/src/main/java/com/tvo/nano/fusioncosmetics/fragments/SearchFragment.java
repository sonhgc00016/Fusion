package com.tvo.nano.fusioncosmetics.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.tvo.nano.adapters.MembershipAdapter;
import com.tvo.nano.fusioncosmetics.Constants;
import com.tvo.nano.fusioncosmetics.R;
import com.tvo.nano.fusioncosmetics.activities.FrameContentActivity;
import com.tvo.nano.json.JSON_SearchCustomerResult;
import com.tvo.nano.models.Customer;
import com.tvo.nano.network.RequestAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends BaseAppFragment implements RequestAPI.IResultApiListener {

    private EditText edtKeyword;
    private ImageView imgSearch;
    private ListView lvCustomer;

    private String sessionid, timestamp, userid;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //set staying page
        Constants.stayPage = Constants.StayPage.SEARCH;

        //set Actionbar values
        setHeadingPage(R.string.search);
        setImageLeftActionbarBackground(R.drawable.actionbar_imgback);
        setImageRightActionbarBackground(R.drawable.actionbar_imgempty);
        setItemSoldCountVisible(false);

        //get user's infomation
        FrameContentActivity frameContentActivity = (FrameContentActivity) getActivity();
        userid = frameContentActivity.getUserid();
        sessionid = frameContentActivity.getSessionid();
        timestamp = frameContentActivity.getTimestamp();

        edtKeyword = (EditText) view.findViewById(R.id.search_edtKeyword);
        imgSearch = (ImageView) view.findViewById(R.id.search_imgSearch);
        lvCustomer = (ListView) view.findViewById(R.id.search_lvCustomer);

        edtKeyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getSearchCustomerResult(edtKeyword.getText().toString().trim());
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edtKeyword.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSearchCustomerResult(edtKeyword.getText().toString().trim());
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edtKeyword.getWindowToken(), 0);
            }
        });
    }

    private void getSearchCustomerResult(String keyword) {
        final String URL = Constants.SERVER_URL + "SearchCustomer";

        Map<String, String> params = new HashMap<>();
        params.put("userid", userid);
        params.put("sessionid", sessionid);
        params.put("timestamp", timestamp);
        params.put("searchtext", keyword);

        RequestAPI.requestAPI(getActivity(), URL, params, Request.Method.POST,
                JSON_SearchCustomerResult.class, this, 2, "SearchCustomerResult");
    }

    @Override
    public void onResult(Object object) {
        if (object != null && object instanceof JSON_SearchCustomerResult) {
            JSON_SearchCustomerResult result = (JSON_SearchCustomerResult) object;
            ArrayList<Customer> arrCustomers = new ArrayList<>();
            if (result.getArrCustomers() != null && result.getArrCustomers().size() > 0) {
                arrCustomers = result.getArrCustomers();
                MembershipAdapter adapter = new MembershipAdapter(getActivity(), arrCustomers);
                lvCustomer.setAdapter(adapter);
                lvCustomer.setVisibility(View.VISIBLE);
            } else {
                lvCustomer.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No result.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
