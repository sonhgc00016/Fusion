package com.tvo.nano.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tvo.nano.fusioncosmetics.Constants;
import com.tvo.nano.fusioncosmetics.activities.FrameContentActivity;
import com.tvo.nano.fusioncosmetics.fragments.LoginFragment;
import com.tvo.nano.fusioncosmetics.R;
import com.tvo.nano.fusioncosmetics.fragments.SalesTrackingFragment;
import com.tvo.nano.models.Products;

import com.tvo.nano.network.CustomAlertDialogManager;

import java.util.ArrayList;

/**
 * Created by Son on 21-Apr-15.
 */
public class ProductSalesTrackingAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Products> mArrayProducts;
    CustomAlertDialogManager dialogManager = new CustomAlertDialogManager();

    public ProductSalesTrackingAdapter(Context ctx, ArrayList<Products> prd) {
        this.mContext = ctx;
        this.mArrayProducts = prd;
    }

    @Override
    public int getCount() {
        return mArrayProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //Inflate item_product_sales_tracking.xml file for each row ( Defined below )
            convertView = inflater.inflate(R.layout.item_product_sales_tracking, null);

            //View Holder Object to contain item_product_sales_tracking.xml file elements
            holder = new ViewHolder();
            holder.tvOrdinalNumber = (TextView) convertView
                    .findViewById(R.id.itemProduct_tvOrdinalNumber);
            holder.tvItemID = (TextView) convertView
                    .findViewById(R.id.itemProduct_tvItemID);
            holder.tvDescription = (TextView) convertView
                    .findViewById(R.id.itemProduct_tvDescription);
            holder.tvSize = (TextView) convertView
                    .findViewById(R.id.itemProduct_tvSize);
            holder.tvPrice = (TextView) convertView
                    .findViewById(R.id.itemProduct_tvPrice);
            holder.imgAddItem = (ImageView) convertView
                    .findViewById(R.id.itemProduct_imgAddItem);

            //Set holder with LayoutInflater
            convertView.setTag(holder);

        } else
            holder = (ViewHolder) convertView.getTag();

        holder.tvOrdinalNumber.setText(String.valueOf(position + 1));
        holder.tvItemID.setText(mArrayProducts.get(position).getProductid());
        holder.tvDescription.setText(mArrayProducts.get(position).getDescription());
        holder.tvSize.setText(mArrayProducts.get(position).getSize());

        double price = Double.parseDouble(mArrayProducts.get(position).getPrice());
        String priceDisplay = String.format("%.2f", price);
        holder.tvPrice.setText(priceDisplay);

        holder.imgAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameContentActivity frameContentActivity = (FrameContentActivity) mContext;
                SalesTrackingFragment fSalesTracking =
                        (SalesTrackingFragment) frameContentActivity
                                .getSupportFragmentManager().findFragmentById(R.id.content_frame);

                String salesdate = fSalesTracking.getEdtDate().getText().toString().trim();
                String salesdatecut = salesdate.replaceAll("[^a-zA-Z0-9.-]", "");
                frameContentActivity.setSalesdatecut(salesdatecut);
                String storeid = fSalesTracking.getStoreid();
                String storename = fSalesTracking.getStorename();
                boolean addItemSecondTime = false;

                frameContentActivity.decodeAndReadJson(salesdatecut);

                if (frameContentActivity.validateDate(salesdate)
                        && salesdate != null && !salesdate.isEmpty()
                        && storename != null && !storename.isEmpty()) {
                    //set item sold count
                    Constants.itemSold = Constants.arrItemSold.size();
                    if (Constants.arrItemSold.size() > 0) {
                        for (Products prd : Constants.arrItemSold) {
                            if (prd.getProditem_id().equals(mArrayProducts.get(position).getProditem_id())
                                    && prd.getStorename().equals(storename)) {
                                //product already exist in item sold list
//                                int quantity = 1;
//                                quantity = Integer.parseInt(prd.getQuantity());
//                                mArrayProducts.get(position).setQuantity(String.valueOf(quantity + 1));
                                Constants.arrItemSold.remove(prd);
                                addItemSecondTime = true;
                                Toast.makeText(mContext, mContext.getResources().getString(R.string.item_already_added),
                                        Toast.LENGTH_SHORT).show();
                                break;
                            } else {
                                mArrayProducts.get(position).setQuantity("0");
                            }
                        }
                    } else {
                        mArrayProducts.get(position).setQuantity("0");
                    }
                    if (mArrayProducts.get(position).getQuantity() == null)
                        mArrayProducts.get(position).setQuantity("0");
                    mArrayProducts.get(position).setStoreid(storeid);
                    mArrayProducts.get(position).setStorename(storename);
                    Constants.arrItemSold.add(mArrayProducts.get(position));
                    Constants.itemSold = Constants.arrItemSold.size();
                    frameContentActivity.setItemSoldCount(String.valueOf(Constants.itemSold));
                    frameContentActivity.encodeAndSaveJson();
                    if (!addItemSecondTime) {
                        //show successfull Toast
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.add_successfully),
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dialogManager.showCustomAlertDialog(mContext, mContext.getResources().getString(R.string.error),
                            mContext.getResources().getString(R.string.something_wrong), true);
                    frameContentActivity.replaceFragment(new LoginFragment(), false);
                }
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView tvOrdinalNumber, tvItemID, tvDescription, tvSize, tvPrice;
        ImageView imgAddItem;
    }
}