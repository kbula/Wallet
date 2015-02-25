package com.bula.Wallet.app.View.Controls;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.bula.Wallet.app.Data.BasicHelper;
import com.bula.Wallet.app.R;
import com.bula.Wallet.app.View.Tabs.IView;

/**
 * Created by Krzysiek on 2014-09-14.
 */
public class IntervalControl {

    private EditText dateFrom;
    private EditText dateTo;
    private Context _context;
    private IView _view;

    public IntervalControl(Context context, IView view)
    {
        _context = context;
        _view = view;
    }

    public void createControl(final View view)
    {
        dateFrom = (EditText) view.findViewById(R.id.editDateFrom);
        dateTo = (EditText) view.findViewById(R.id.editDateTo);

        dateFrom.setText(BasicHelper.getThisWeek().getBeginDate());
        dateTo.setText(BasicHelper.getDateToday());
        dateTo.setFocusable(false);
        dateFrom.setFocusable(false);

        dateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.setDate(_context, dateFrom, _view);
            }
        });

        dateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.setDate(_context, dateTo, _view);
                //_view.executeData();
            }
        });
    }

    public EditText getDateFrom()
    {
        return dateFrom;
    }

    public EditText getDateTo()
    {
        return dateTo;
    }

    public IView getView()
    {
        return _view;
    }


}
