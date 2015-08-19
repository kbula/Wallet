package com.bula.Wallet.app.View.Tabs;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import com.bula.Wallet.app.Core.DataBase.DataBaseHelper;
import com.bula.Wallet.app.Core.Data.IntervalDateTime;
import com.bula.Wallet.app.R;
import com.bula.Wallet.app.View.Controls.IntervalControl;
import com.bula.Wallet.app.Core.WalletExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysiek on 2014-08-09.
 */
public class AllView implements IView
{

    Spinner type;
    Context _context;
    DataBaseHelper db;
    List<String> listType;
    IntervalControl intervalControl;
    ExpandableListView listView;

    public AllView( Context context, DataBaseHelper dataBaseHelper)
    {
        _context = context;
        db =  dataBaseHelper;

    }

    @Override
    public void createView(View view) {
        type = (Spinner) view.findViewById(R.id.spinnerType);
        listView= (ExpandableListView) view.findViewById(R.id.expandableListView);

        intervalControl = new IntervalControl(_context, this);
        intervalControl.createControl(view);

        listType = new ArrayList<String>();
        listType.add("wszystkie");
        listType.addAll(db.getAllTypes());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(_context, R.layout.simple_spiner_item, listType );
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        type.setAdapter(adapter);

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedIndex = type.getSelectedItemPosition();
                fillAllItem(db,selectedIndex, intervalControl.getDateFrom().getText().toString(), intervalControl.getDateTo().getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void executeData() {
        type.requestFocus();

        //fillAllItem(dataBaseHelper, type.getSelectedItemPosition()+1,"","");
        fillAllItem(db,type.getSelectedItemPosition(), intervalControl.getDateFrom().getText().toString(), intervalControl.getDateTo().getText().toString());
    }

    private void fillAllItem(DataBaseHelper dataBaseHelper, int id, String fromDate, String toDate)
    {
        //ArrayAdapter<String> adapter;
        WalletExpandableListAdapter adapter;
/*        if(id == 0)
            adapter = new ArrayAdapter<String>(_context, R.layout.listview_all_item, dataBaseHelper.getAllItem() );
        else {*/
        if(!fromDate.isEmpty() && !toDate.isEmpty())
            //adapter = new ArrayAdapter<String>(_context, R.layout.listview_all_item, dataBaseHelper.getAllItemFromType(id,new IntervalDateTime(fromDate,toDate)));
            adapter = new WalletExpandableListAdapter(_context, dataBaseHelper.getAllItemFromType(id,new IntervalDateTime(fromDate,toDate)), this);
        else
            //adapter = new ArrayAdapter<String>(_context, R.layout.listview_all_item, dataBaseHelper.getAllItemFromType(id));
            adapter = new WalletExpandableListAdapter(_context, dataBaseHelper.getAllItemFromType(id,new IntervalDateTime(fromDate,toDate)), this);
       // }
        listView.setAdapter(adapter);
        //allItem.setAdapter(adapter);
    }

}
