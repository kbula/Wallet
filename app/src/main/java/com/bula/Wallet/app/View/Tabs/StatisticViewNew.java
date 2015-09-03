package com.bula.Wallet.app.View.Tabs;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bula.Wallet.app.Core.BasicHelper;
import com.bula.Wallet.app.Core.Data.IntervalDateTime;
import com.bula.Wallet.app.Core.IntervalDateFactory;
import com.bula.Wallet.app.Core.Intervals;
import com.bula.Wallet.app.R;

import com.bula.Wallet.app.Core.DataBase.DataBaseHelper;
import com.bula.Wallet.app.View.Controls.PercentViewDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Krzysiek on 2014-11-29.
 */
public class StatisticViewNew implements IView {

    Context _context;
    DataBaseHelper _dataBaseHelper;
    Button prev,next;
    TextView monthName , cost;
    ListView listViewInterval;
    PercentView percentView;
    int actualMonthnumber;
    List<IntervalDateTime> listIntervalDateTimes;
    int selectedPosition = -1;
    Calendar calendar;

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
        listViewInterval = (ListView) view.findViewById(R.id.listViewInterval);
        //percentView = (PercentView) view.findViewById(R.id.percentViewCost);


        calendar = Calendar.getInstance();
        calendar.setTime(IntervalDateFactory.getIntervalDate(Intervals.ThisMonth).getBegin());
        displayData(calendar);

       // percentView.fill(IntervalDateFactory.getIntervalDate(Intervals.ThisWeek));

        prev.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH,-1);
                displayData(calendar);
                selectItemList(0);

               // percentView.fill(listIntervalDateTimes.get(0));
            }
        });

        next.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH,1);
                displayData(calendar);
                selectItemList(0);
               // percentView.fill(listIntervalDateTimes.get(0));
            }
        });

        listViewInterval.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listIntervalDateTimes != null) {
                    // percentView.fill(listIntervalDateTimes.get(position));
                    if (Float.parseFloat(_dataBaseHelper.getSumCost(listIntervalDateTimes.get(position))) != 0.0f) {
                        PercentViewDialog.showDialog(_context, listIntervalDateTimes.get(position));
                        return;
                    }
                    Toast.makeText(_context, "brak danych", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void executeData() {

       // monthName.setText(BasicHelper.getMonthName(actualMonthnumber));
       // percentView.fill(IntervalDateFactory.getIntervalDate(Intervals.ThisWeek));
        displayData(calendar);
    }

    private void displayData(Calendar calendar)
    {
        actualMonthnumber = calendar.get(Calendar.MONTH);
        monthName.setText(BasicHelper.getMonthName(actualMonthnumber,_context)+ " " + calendar.get(Calendar.YEAR));
        cost.setText(_dataBaseHelper.getSumCost(BasicHelper.getSelectedMonth(calendar)));

        listIntervalDateTimes =  BasicHelper.getAllWeekInMonth(calendar.get(Calendar.MONTH),calendar.get(Calendar.YEAR));
        listIntervalDateTimes.add(0,BasicHelper.getSelectedMonth(calendar));
        List<String> listinterval = new ArrayList<String>();
        int actualWeekId=-1;

        IntervalDateTime thisWeek = IntervalDateFactory.getIntervalDate(Intervals.ThisWeek);

        for (IntervalDateTime item : listIntervalDateTimes)
        {
            if(item != null)
            {
                listinterval.add(item.getBeginDate() + " - " + item.getEndDate() + "\t" +" " +_dataBaseHelper.getSumCost(item));
                if(item.getBeginDate().equals(thisWeek.getBeginDate()) && item.getEndDate().equals(thisWeek.getEndDate()))
                {
                    actualWeekId = listinterval.size()-1;
                }
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(_context,android.R.layout.simple_list_item_1, listinterval);
        adapter.setNotifyOnChange(true);
        listViewInterval.setAdapter(adapter);

        if(actualWeekId>=0) {
            selectItemList(actualWeekId);
        }

    }

    private void selectItemList(int id)
    {
        listViewInterval.requestFocusFromTouch();
        listViewInterval.setSelection(id);
    }

}
