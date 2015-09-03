package com.bula.Wallet.app.View.Controls;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.bula.Wallet.app.Core.Data.IntervalDateTime;
import com.bula.Wallet.app.R;
import com.bula.Wallet.app.View.Tabs.IView;
import com.bula.Wallet.app.View.Tabs.PercentView;

/**
 * Created by Krzysiek on 2015-08-21.
 */
public class PercentViewDialog {

    public static void showDialog(Context context, final IntervalDateTime intervalDateTime)
    {
        PercentView percentView;

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View dateView = layoutInflater.inflate(R.layout.percent_view_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(dateView);

        percentView = (PercentView) dateView.findViewById(R.id.percentViewCost);

        percentView.fillDialog(intervalDateTime);

        alertDialogBuilder.setCancelable(true).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
