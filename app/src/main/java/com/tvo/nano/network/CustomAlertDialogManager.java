package com.tvo.nano.network;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tvo.nano.fusioncosmetics.activities.FrameContentActivity;
import com.tvo.nano.fusioncosmetics.fragments.LoginFragment;
import com.tvo.nano.fusioncosmetics.R;

/**
 * Created by Son on 23-Apr-15.
 */
public class CustomAlertDialogManager {
    private Dialog alertDialog;
    private TextView tvTitle;
    private TextView tvMessage;
    private ImageView imgOk;

    public void showCustomAlertDialog(final Context context, String title, final String message,
                                      Boolean status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // Get the layout inflater
        LayoutInflater inflater = LayoutInflater.from(context);
        View customView = inflater.inflate(R.layout.custom_alert_dialog, null);

        tvTitle = (TextView) customView.findViewById(R.id.customDialog_tvTitle);
        tvTitle.setText(title);

        tvMessage = (TextView) customView.findViewById(R.id.customDialog_tvMessage);
        tvMessage.setText(message);

        imgOk = (ImageView) customView.findViewById(R.id.customDialog_imgOk);
        imgOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (message.contains(context.getString(R.string.invalid_session))) {
                    FrameContentActivity frameContentActivity = (FrameContentActivity) context;
                    frameContentActivity.replaceFragment(new LoginFragment(), false);
                }
                alertDialog.dismiss();
            }
        });

        if (status != null) {
            if (status) {
                //show icon normal
            } else {
                //show icon fail
            }
        }
        ;

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(customView);

        alertDialog = builder.create();
        alertDialog.show();

    }
}
