package com.bula.Wallet.app.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.bula.Wallet.app.Data.BasicHelper;
import com.bula.Wallet.app.Data.DataBaseConnection;
import com.bula.Wallet.app.Data.DataBaseHelper;
import com.bula.Wallet.app.Data.WalletData;
import com.bula.Wallet.app.R;

/**
 * Created by Krzysiek on 2014-10-19.
 */
public class EditWalletDialog {

    public static void editData(final Context context,final WalletData walletData, final IView view)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View dataView = layoutInflater.inflate(R.layout.edit_wallet_data_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(dataView);

        final EditText cost = (EditText) dataView.findViewById(R.id.editTextCost);
        final EditText comment = (EditText) dataView.findViewById(R.id.editTextNotes);
        final EditText date = (EditText) dataView.findViewById(R.id.editTextDate);
        final EditText time = (EditText) dataView.findViewById(R.id.editTextTime);
        final Spinner type = (Spinner) dataView.findViewById(R.id.spinnerType);

        final DataBaseHelper db = DataBaseConnection.getConnection(context);//new DataBaseHelper(context);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.simple_spiner_item, db.getAllTypes() );
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        type.setAdapter(adapter);
        type.setSelection(db.getTypeId(walletData.getType())-1);

        cost.setText(walletData.getCost().toString());
        comment.setText(walletData.getComment().toString());
        date.setText( BasicHelper.getDate(BasicHelper.stringToDate(walletData.getDate())) );
        time.setText( BasicHelper.getTime(BasicHelper.stringToDateTime(walletData.getDate())) );

        date.setFocusable(false);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.setDate(context, date, null);
            }
        });

        time.setFocusable(false);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.setTime(context,time);
            }
        });

        alertDialogBuilder.setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                db.UpdateData(new WalletData(walletData.getId(),Double.parseDouble(cost.getText().toString()), type.getSelectedItem().toString(), comment.getText().toString(),date.getText().toString(),time.getText().toString()));
                view.executeData();
            }
        }).setNegativeButton("Anuluj",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    };
                }).setNeutralButton("Usuń",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.DeleteData(walletData.getId());
                        view.executeData();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}
