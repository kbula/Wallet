package com.bula.Wallet.app.View;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bula.Wallet.app.Data.BasicHelper;
import com.bula.Wallet.app.Data.DataBaseHelper;
import com.bula.Wallet.app.Data.IntervalDateTime;
import com.bula.Wallet.app.Data.FillDiagram;
import com.bula.Wallet.app.Data.Statistic;
import com.bula.Wallet.app.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Krzysiek on 2014-08-09.
 */
public class StatisticsView implements IView {

    Context _context;
    DataBaseHelper db;
    TextView cost, monthName;
    LinearLayout setDateLayout;
    Spinner spinnerStatistic;
    PercentView percentView;
    List<Statistic> _listStatistic;
    List<String> _listStatisticName;
    IntervalControl intervalControl ;

    public StatisticsView(Context context, DataBaseHelper dataBaseHelper)
    {
        _context = context;
        db = dataBaseHelper;
        _listStatistic = new ArrayList<Statistic>();
        _listStatistic.add(new Statistic("Koszt aktualnego tygodnia",BasicHelper.getThisWeek()));
        _listStatistic.add(new Statistic("Koszt poprzedniego tygodnia",BasicHelper.getLastWeek()));
        _listStatistic.add(new Statistic("Koszt aktualnego miesiaca",BasicHelper.getThisMonth()));
        _listStatistic.add(new Statistic("Koszt poprzedniego miesiaca",BasicHelper.getLatsMonth()));
        _listStatistic.add(new Statistic("Wybierz okres",null));

        _listStatisticName = new ArrayList<String>();

        for(Statistic item : _listStatistic)
        {
            _listStatisticName.add(item.getName());
        }
    }

    @Override
    public void createView(View view) {
        percentView = (PercentView) view.findViewById(R.id.percentView);
        cost = (TextView) view.findViewById(R.id.textViewTotalCost);
        setDateLayout = (LinearLayout) view.findViewById(R.id.setDateLayout);
        monthName = (TextView)view.findViewById(R.id.monthName);

       // actualWeek.setBackgroundResource(R.drawable.text_blue);

        intervalControl = new IntervalControl(_context,this);
        intervalControl.createControl(view);

        spinnerStatistic = (Spinner)view.findViewById(R.id.spinnerStatisctic);

        spinnerStatistic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedIndex = spinnerStatistic.getSelectedItemPosition();
                if(selectedIndex < _listStatisticName.size()-1) {
                    percentView.fill(fillData(_listStatistic.get(selectedIndex).getInterval()));
                    cost.setText(" " + db.getSumCost(_listStatistic.get(selectedIndex).getInterval()));

                    Calendar calendar  = Calendar.getInstance();
                    calendar.setTime(_listStatistic.get(selectedIndex).getInterval().getBegin());
                    monthName.setText(BasicHelper.getMonthName(_listStatistic.get(selectedIndex).getInterval().getBegin().getMonth() )+
                            " " + calendar.get(Calendar.YEAR));
                    setDateLayout.getLayoutParams().height=0;
                    if(view != null)
                        view.invalidate();
                }
                else if(selectedIndex ==_listStatisticName.size()-1 )
                {
                    setDateLayout.getLayoutParams().height=120;
                    if(view != null)
                        view.invalidate();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(_context, R.layout.simple_spiner_item, _listStatisticName );
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerStatistic.setAdapter(adapter);

    }

    @Override
    public void executeData() {

        if(_listStatistic.get(spinnerStatistic.getSelectedItemPosition()).getInterval()!= null) {
            percentView.fill(fillData(_listStatistic.get(spinnerStatistic.getSelectedItemPosition()).getInterval()));
            cost.setText(db.getSumCost(_listStatistic.get(spinnerStatistic.getSelectedItemPosition()).getInterval()));
        }
        else
        {
            IntervalDateTime intervalDateTime = new IntervalDateTime(intervalControl.getDateFrom().getText().toString(), intervalControl.getDateTo().getText().toString());
            percentView.fill(fillData(intervalDateTime));
            cost.setText(db.getSumCost(intervalDateTime));
        }
    }

    public List<FillDiagram> fillData(IntervalDateTime dateTimeHelper)
    {
        float totalCost =  Float.parseFloat(db.getSumCost(dateTimeHelper));
        float typeCost;

        int[] colorTable = BasicHelper.createColorTable(_context);
        List<FillDiagram> listFillDiagram = new ArrayList<FillDiagram>();
        List<String> listType = db.getAllTypes();
        for(int i=1; i<8; i++)
        {
            typeCost = Float.parseFloat(db.getSumCostTypeInterval(i,dateTimeHelper));
            if(typeCost != 0)
            {
                listFillDiagram.add(new FillDiagram(colorTable[i-1], (typeCost/totalCost), listType.get(i-1), typeCost));
            }else
            {
                listFillDiagram.add(new FillDiagram(colorTable[i-1], 0, listType.get(i-1), 0));
            }
        }
        return listFillDiagram;
    }

}
