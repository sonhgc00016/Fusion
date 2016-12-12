package com.tvo.nano.fusioncosmetics.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tvo.nano.fusioncosmetics.Constants;
import com.tvo.nano.fusioncosmetics.activities.FrameContentActivity;
import com.tvo.nano.fusioncosmetics.R;
import com.tvo.nano.models.Products;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemSoldFragment extends BaseAppFragment {

    private TextView tvDate;
    private String sessionid, timestamp, userid;
    private FrameContentActivity frameContentActivity;
    private ArrayList<Products> arrItemSold = new ArrayList<>();
    private ArrayList<String> arrStoreName = new ArrayList<>();

    public ItemSoldFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_sold, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //set staying page
        Constants.stayPage = Constants.StayPage.ITEM_SOLD;

        //set Actionbar values
        setHeadingPage(R.string.item_sold);
        setImageRightActionbarBackground(R.drawable.actionbar_imgsubmit);
        setImageLeftActionbarBackground(R.drawable.actionbar_imgback);
        setItemSoldCountVisible(false);

        //get user's infomation
        frameContentActivity = (FrameContentActivity) getActivity();

        sessionid = frameContentActivity.getSessionid();
        timestamp = frameContentActivity.getTimestamp();
        userid = frameContentActivity.getUserid();

        //set date
        tvDate = (TextView) view.findViewById(R.id.itemSold_tvDate);
        tvDate.setText(getResources().getString(R.string.date) + ": " + frameContentActivity.getSalesdate());

        Map<String, ArrayList<Products>> itemSoldByStoreMap = new HashMap<>();
        arrItemSold = Constants.arrItemSold;
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

        decodeAndReadJson(frameContentActivity.getSalesdatecut());

        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (Constants.arrItemSold.size() > 0 && arrStoreName.size() > 0) {
            for (String store : arrStoreName) {
                final ArrayList<Products> arrItemSoldByStore;
                arrItemSoldByStore = itemSoldByStoreMap.get(store);

                for (int i = 0; i < arrItemSoldByStore.size(); i++) {
                    View v = inflater.inflate(R.layout.item_itemsold, null);

                    if (i == arrItemSoldByStore.size() - 1) {
                        //setStorename
                        TextView tvStore = (TextView) v.findViewById(R.id.itemSold_tvStore);
                        tvStore.setText(getResources().getString(R.string.store) + " " + store);
                        tvStore.setVisibility(View.VISIBLE);
                    }

                    TextView tvOrdinalNumber = (TextView) v.findViewById(R.id.itemSold_tvOrdinalNumber);
                    tvOrdinalNumber.setText(String.valueOf(arrItemSoldByStore.size() - i));

                    TextView tvItemID = (TextView) v.findViewById(R.id.itemSold_tvItemID);
                    tvItemID.setText(arrItemSoldByStore.get(i).getProditem_id());

                    TextView tvDescription = (TextView) v.findViewById(R.id.itemSold_tvDescription);
                    tvDescription.setText(arrItemSoldByStore.get(i).getDescription());

                    TextView tvSize = (TextView) v.findViewById(R.id.itemSold_tvSize);
                    tvSize.setText(arrItemSoldByStore.get(i).getSize());

                    TextView tvPrice = (TextView) v.findViewById(R.id.itemSold_tvPrice);
                    double price = Double.parseDouble(arrItemSoldByStore.get(i).getPrice());
                    String priceDisplay = String.format("%.2f", price);
                    tvPrice.setText(priceDisplay);

                    final EditText tvQuantity = (EditText) v.findViewById(R.id.itemSold_edtQuantity);
                    tvQuantity.setText(arrItemSoldByStore.get(i).getQuantity());

                    ImageView imgPlus = (ImageView) v.findViewById(R.id.itemSold_imgPlus);
                    final int finalI = i;
                    imgPlus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int quanity = Integer.parseInt(tvQuantity.getText().toString());
                            quanity++;
                            tvQuantity.setText(String.valueOf(quanity));
                            Constants.arrItemSold.get(finalI).setQuantity(String.valueOf(quanity));
                            for (Products products : Constants.arrItemSold) {
                                if (products.getStorename().equals(arrItemSoldByStore.get(finalI).getStorename())
                                        && products.getProductid().equals(arrItemSoldByStore.get(finalI).getProductid())) {
                                    products.setQuantity(tvQuantity.getText().toString().trim());
                                }
                            }
                            encodeAndSaveJson();
                        }
                    });

                    ImageView imgMinus = (ImageView) v.findViewById(R.id.itemSold_imgMinus);
                    imgMinus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int quanity = Integer.parseInt(tvQuantity.getText().toString());
                            quanity--;
                            if (quanity >= 0)
                                tvQuantity.setText(String.valueOf(quanity));
                            for (Products products : Constants.arrItemSold) {
                                if (products.getStorename().equals(arrItemSoldByStore.get(finalI).getStorename())
                                        && products.getProductid().equals(arrItemSoldByStore.get(finalI).getProductid())) {
                                    products.setQuantity(tvQuantity.getText().toString().trim());
                                }
                            }
                            encodeAndSaveJson();
                        }
                    });
                    // insert into main view
                    ViewGroup insertPoint = (ViewGroup) view.findViewById(R.id.itemSold_lnlProduct);
                    insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                }
            }
        }
    }
}
