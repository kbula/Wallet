package com.bula.Wallet.app.View.Controls;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.bula.Wallet.app.Core.BasicHelper;
import com.bula.Wallet.app.R;
import com.bula.Wallet.app.View.Tabs.IView;

import java.util.Calendar;

/**
 * Created by Krzysiek on 2014-08-23.
 */
public class DatePickerDialog {

    public static void setDate(Context context,final EditText editText, final IView view)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View dateView = layoutInflater.inflate(R.layout.date_picker_dialog, null);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(dateView);

        final DatePicker datePicker = (DatePicker)dateView.findViewById(R.id.datePicker);
        String date = editText.getText().toString();
        String[] dateSplit;
        if(!date.isEmpty())
        {
            dateSplit = date.split("/");
            if(dateSplit != null)
                datePicker.updateDate(Integer.parseInt(dateSplit[0]),Integer.parseInt(dateSplit[1])-1,Integer.parseInt(dateSplit[2]));
        }

        alertDialogBuilder.setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Calendar calender = Calendar.getInstance();
                calender.set(Calendar.YEAR,datePicker.getYear());
                calender.set(Calendar.MONTH,datePicker.getMonth());
                calender.set(Calendar.DAY_OF_MONTH,datePicker.getDayOfMonth());

                editText.setText(BasicHelper.getDate(calender.getTime()));
                if(view != null)
                    view.executeData();
            }
        }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    };
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    public static void setTime(Context context,final EditText editText)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View tomeView = layoutInflater.inflate(R.layout.time_picker_dialog, null);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(tomeView);

        final TimePicker timePicker = (TimePicker)tomeView.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        String date = editText.getText().toString();
        String[] dateSplit;
        if(!date.isEmpty())
        {
            dateSplit = date.split(":");
            if(dateSplit != null) {
                timePicker.setCurrentHour(Integer.parseInt(dateSplit[0]));
                timePicker.setCurrentMinute(Integer.parseInt(dateSplit[1]));
            }

        }

        alertDialogBuilder.setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String minute;
                if(timePicker.getCurrentMinute() < 10)
                {
                    minute = "0"+timePicker.getCurrentMinute();
                }
                else
                {
                    minute=timePicker.getCurrentMinute().toString();
                }

                editText.setText(timePicker.getCurrentHour()+":"+minute);
            }
        }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    };
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
