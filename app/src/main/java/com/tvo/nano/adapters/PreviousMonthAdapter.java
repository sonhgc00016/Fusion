package com.tvo.nano.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tvo.nano.fusioncosmetics.R;
import com.tvo.nano.models.CurrentMonthData;
import com.tvo.nano.models.PreviousMonthData;

import java.util.ArrayList;

/**
 * Created by Son on 08-May-15.
 */
public class PreviousMonthAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<PreviousMonthData> arrPrevious;

    public PreviousMonthAdapter(Context ctx, ArrayList<PreviousMonthData> arrPrevious) {
        this.mContext = ctx;
        this.arrPrevious = arrPrevious;
    }

    @Override
    public int getCount() {
        return arrPrevious.size();
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

        holder.tvBrand.setText(arrPrevious.get(position).getBrand());
        if (arrPrevious.get(position).getMtd().indexOf(".") != 0) {
            holder.tvMTD.setText(arrPrevious.get(position).getMtd()
                    .substring(0, arrPrevious.get(position).getMtd().indexOf(".")));
        } else
            holder.tvMTD.setText(arrPrevious.get(position).getMtd());

        if (arrPrevious.get(position).getTarget().indexOf(".") != 0) {
            holder.tvTarget.setText(arrPrevious.get(position).getTarget()
                    .substring(0, arrPrevious.get(position).getTarget().indexOf(".")));
        } else
            holder.tvTarget.setText(arrPrevious.get(position).getTarget());

        double achieved = Double.parseDouble(arrPrevious.get(position).getAchieved());
        String achievedDisplay = String.format("%.2f", achieved);

        holder.tvAchieved.setText(achievedDisplay + "%");

        return convertView;
    }

    class ViewHolder {
        TextView tvBrand, tvMTD, tvTarget, tvAchieved;
    }
}
