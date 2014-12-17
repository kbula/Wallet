package com.bula.Wallet.app.View;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bula.Wallet.app.Data.BasicHelper;
import com.bula.Wallet.app.Data.IntervalDateTime;
import com.bula.Wallet.app.R;

import com.bula.Wallet.app.Data.DataBaseHelper;

import java.util.Calendar;


/**
 * Created by Krzysiek on 2014-11-29.
 */
public class StatisticViewNew implements IView {

    Context _context;
    DataBaseHelper _dataBaseHelper;
    Button prev,next;
    TextView monthName , cost;
    int actualMonthnumber;

    public StatisticViewNew(Context context, DataBaseHelper dataBaseHelper)
    {
        _context= context;
        _dataBaseHelper = dataBaseHelper;

    }

    @Override
    public void createView(View view) {
        prev = (Button) view.findViewById(R.id.buttonPrev);
        next = (Button) view.findViewById(R.id.buttonNext);
        monthName = (TextView) view.findViewById(R.id.monthName);
        cost = (TextView) view.findViewById(R.id.cost);

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(BasicHelper.getThisMonth().getBegin());
        displayData(calendar);

        prev.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH,-1);
                displayData(calendar);
            }
        });

        next.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH,1);
                displayData(calendar);
            }
        });

    }

    @Override
    public void executeData() {

       // monthName.setText(BasicHelper.getMonthName(actualMonthnumber));
    }

    private void displayData(Calendar calendar)
    {
        actualMonthnumber = calendar.get(Calendar.MONTH);
        monthName.setText(BasicHelper.getMonthName(actualMonthnumber,_context)+ " " + calendar.get(Calendar.YEAR));
        cost.setText(_dataBaseHelper.getSumCost(BasicHelper.getSelectedMonth(calendar)));

        IntervalDateTime[] listIntervalDateTimes =  BasicHelper.getAllWeekInMonth(calendar.get(Calendar.MONTH),calendar.get(Calendar.YEAR));
        String text = new String();

        for (IntervalDateTime item : listIntervalDateTimes)
        {
            if(item != null)
            {
                text+= item.getBeginDate() + " \t " + item.getEndDate() + "\n" +" " +_dataBaseHelper.getSumCost(item)+"\n";
            }
        }

        cost.setText(cost.getText()+"\n" + text);
    }

}
