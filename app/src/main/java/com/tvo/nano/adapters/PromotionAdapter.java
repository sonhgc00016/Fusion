package com.tvo.nano.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tvo.nano.fusioncosmetics.R;
import com.tvo.nano.json.JSON_ActivityPromo;
import com.tvo.nano.json.JSON_SalesReportResult;
import com.tvo.nano.models.PromoDetails;

import java.util.ArrayList;

/**
 * Created by Son on 05-May-15.
 */
public class PromotionAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<JSON_ActivityPromo> mArrayActivityPromo;

    public PromotionAdapter(Context ctx, ArrayList<JSON_ActivityPromo> activityPromo) {
        this.mContext = ctx;
        this.mArrayActivityPromo = activityPromo;
    }

    @Override
    public int getCount() {
        return mArrayActivityPromo.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //Inflate item_promotion.xml file for each row ( Defined below )
            if (mArrayActivityPromo.get(position).getArrPromoDetails() != null) {
                convertView = inflater.inflate(R.layout.item_promotion, null);

                //View Holder Object to contain item_promotion.xml file elements
                holder = new ViewHolder();
                holder.tvStore = (TextView) convertView
                        .findViewById(R.id.itemPromotion_tvStore);

                //Set holder with LayoutInflater
                convertView.setTag(holder);
                holder.tvStore.setText(mArrayActivityPromo.get(position).getStorename());

                if (mArrayActivityPromo.get(position).getArrPromoDetails() != null
                        && mArrayActivityPromo.get(position).getArrPromoDetails().size() > 0) {
                    ArrayList<PromoDetails> arrPromoDetails = mArrayActivityPromo.get(position).getArrPromoDetails();
                    for (int i = 0; i < arrPromoDetails.size(); i++) {
                        View v = inflater.inflate(R.layout.item_promo_detail, null);

                        // fill in any details dynamically here
                        TextView tvProduct = (TextView) v.findViewById(R.id.itemPromotionDetail_tvProduct);
                        tvProduct.setText(arrPromoDetails.get(i).getProductDescription());

                        TextView tvDescription = (TextView) v.findViewById(R.id.itemPromotionDetail_tvDescription);
                        tvDescription.setText(arrPromoDetails.get(i).getDescription());

                        TextView tvDateinfo = (TextView) v.findViewById(R.id.itemPromotionDetail_tvDateinfo);
                        tvDateinfo.setText("(" + arrPromoDetails.get(i).getDateinfo() + ")");

                        // insert into main view
                        ViewGroup insertPoint = (ViewGroup) convertView.findViewById(R.id.itemPromotion_llChild);
                        insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    }
                }
            } else {//if data is null inflate new empty view
                convertView = inflater.inflate(R.layout.item_promotion1, null);
            }
        }

        return convertView;
    }

    class ViewHolder {
        TextView tvStore;
    }
}
