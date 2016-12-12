package com.tvo.nano.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tvo.nano.fusioncosmetics.R;
import com.tvo.nano.json.JSON_ActivityPromo;
import com.tvo.nano.models.CurrentMonthData;

import java.util.ArrayList;

/**
 * Created by Son on 08-May-15.
 */
public class CurrentMonthAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<CurrentMonthData> arrCurrent;

    public CurrentMonthAdapter(Context ctx, ArrayList<CurrentMonthData> arrCurrent) {
        this.mContext = ctx;
        this.arrCurrent = arrCurrent;
    }

    @Override
    public int getCount() {
        return arrCurrent.size();
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

            //Inflate item_report_month.xml file for each row ( Defined below )
            convertView = inflater.inflate(R.layout.item_report_month, null);

            //View Holder Object to contain item_report_month.xml file elements
            holder = new ViewHolder();
            holder.tvBrand = (TextView) convertView
                    .findViewById(R.id.itemReportMonth_tvBrand);
            holder.tvMTD = (TextView) convertView
                    .findViewById(R.id.itemReportMonth_tvMTD);
            holder.tvTarget = (TextView) convertView
                    .findViewById(R.id.itemReportMonth_tvTarget);
            holder.tvAchieved = (TextView) convertView
                    .findViewById(R.id.itemReportMonth_tvAchieved);

            //Set holder with LayoutInflater
            convertView.setTag(holder);

        } else
            holder = (ViewHolder) convertView.getTag();

        holder.tvBrand.setText(arrCurrent.get(position).getBrand());
        if (arrCurrent.get(position).getMtd().indexOf(".") != 0) {
            holder.tvMTD.setText(arrCurrent.get(position).getMtd()
                    .substring(0, arrCurrent.get(position).getMtd().indexOf(".")));
        } else
            holder.tvMTD.setText(arrCurrent.get(position).getMtd());

        if (arrCurrent.get(position).getTarget().indexOf(".") != 0) {
            holder.tvTarget.setText(arrCurrent.get(position).getTarget()
                    .substring(0, arrCurrent.get(position).getTarget().indexOf(".")));
        } else
            holder.tvTarget.setText(arrCurrent.get(position).getTarget());

        double achieved = Double.parseDouble(arrCurrent.get(position).getAchieved());
        String achievedDisplay = String.format("%.2f", achieved);

        holder.tvAchieved.setText(achievedDisplay + "%");

        return convertView;
    }

    class ViewHolder {
        TextView tvBrand, tvMTD, tvTarget, tvAchieved;
    }
}
