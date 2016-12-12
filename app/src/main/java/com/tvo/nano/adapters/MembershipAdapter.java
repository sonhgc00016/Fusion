package com.tvo.nano.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tvo.nano.fusioncosmetics.R;
import com.tvo.nano.fusioncosmetics.activities.FrameContentActivity;
import com.tvo.nano.fusioncosmetics.fragments.MembershipUpdateProfileFragment;
import com.tvo.nano.models.Customer;

import java.util.ArrayList;

/**
 * Created by Son on 2015-06-11.
 */
public class MembershipAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Customer> mArrayCustomers;

    public MembershipAdapter(Context ctx, ArrayList<Customer> cus) {
        this.mContext = ctx;
        this.mArrayCustomers = cus;
    }

    @Override
    public int getCount() {
        return mArrayCustomers.size();
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
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //Inflate item_customer_search.xml file for each row ( Defined below )
            convertView = inflater.inflate(R.layout.item_customer_search, null);

            //View Holder Object to contain item_customer_search.xml file elements
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView
                    .findViewById(R.id.item_customer_tvName);
            holder.tvMembershipNo = (TextView) convertView
                    .findViewById(R.id.item_customer_tvMembershipNo);
            holder.tvDateOfIssue = (TextView) convertView
                    .findViewById(R.id.item_customer_tvDateOfIssue);
            holder.tvStatus = (TextView) convertView
                    .findViewById(R.id.item_customer_tvStatus);
            holder.imgEdit = (ImageView) convertView
                    .findViewById(R.id.item_customer_imgEdit);

            //Set holder with LayoutInflater
            convertView.setTag(holder);

        } else
            holder = (ViewHolder) convertView.getTag();

        holder.tvName.setText(mArrayCustomers.get(position).getFullname());
        holder.tvMembershipNo.setText(mArrayCustomers.get(position).getMembership_number());
        holder.tvDateOfIssue.setText(mArrayCustomers.get(position).getDateofissue());
        holder.tvStatus.setText(mArrayCustomers.get(position).getCust_status());
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customerId = mArrayCustomers.get(position).getCustomer_id();
                String salutation = mArrayCustomers.get(position).getSalutation();
                String fullname = mArrayCustomers.get(position).getFullname();
                String gender = mArrayCustomers.get(position).getGender();
                String nric_fin = mArrayCustomers.get(position).getNric_pass_number();
                String dob = mArrayCustomers.get(position).getDateofbirth();
                String nationalityId = mArrayCustomers.get(position).getNationality_id();
                String raceId = mArrayCustomers.get(position).getRace_id();
                String email = mArrayCustomers.get(position).getEmail();
                String address = mArrayCustomers.get(position).getAddress();
                String postal_code = mArrayCustomers.get(position).getPostalcode();
                String handphone = mArrayCustomers.get(position).getHandphone();
                String home_office = mArrayCustomers.get(position).getHomephone();
                String status = mArrayCustomers.get(position).getMaritalstatus();
                FrameContentActivity frameContentActivity = (FrameContentActivity) mContext;
                frameContentActivity.replaceFragment(new MembershipUpdateProfileFragment()
                        .newInstance(customerId, salutation, fullname, gender, nric_fin, dob, nationalityId,
                                raceId, email, address, postal_code, handphone, home_office, status), true);
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView tvName, tvMembershipNo, tvDateOfIssue, tvStatus;
        ImageView imgEdit;
    }
}
