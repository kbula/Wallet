package com.bula.Wallet.app.View;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bula.Wallet.app.Data.BasicHelper;
import com.bula.Wallet.app.Data.DataBaseHelper;
import com.bula.Wallet.app.R;
import com.bula.Wallet.app.Data.WalletData;

/**
 * Created by Krzysiek on 2014-08-09.
 */
public class MainView implements IView {

    EditText cost, notes, date, time;
    Button add;
    Spinner type;
    Context _context;
    DataBaseHelper db;

    public MainView(Context context, DataBaseHelper dataBaseHelper)
    {
        _context = context;
        db = dataBaseHelper;
    }


    @Override
    public void createView(final View view) {
        type = (Spinner) view.findViewById(R.id.spinnerType);
        cost = (EditText) view.findViewById(R.id.editTextCost);
        notes = (EditText) view.findViewById(R.id.editTextNotes);
        date = (EditText) view.findViewById(R.id.editTextDate);
        time = (EditText) view.findViewById(R.id.editTextTime);
        add = (Button) view.findViewById(R.id.buttonAdd);

        date.setText(BasicHelper.getDateToday());
        date.setFocusable(false);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.setDate(_context, date, null);
            }
        });

        time.setText(BasicHelper.getTimeNow());
        time.setFocusable(false);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.setTime(_context,time);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(_context, R.layout.simple_spiner_item, db.getAllTypes() );
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        type.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cost.getText().toString().isEmpty()) {

                    db.addData(new WalletData(Double.parseDouble(cost.getText().toString()), type.getSelectedItem().toString(), notes.getText().toString(),date.getText().toString(),time.getText().toString()));
                    Toast.makeText(_context, "Dodano", Toast.LENGTH_SHORT).show();
                    cost.setText("");
                    notes.setText("");
                }else
                    Toast.makeText(_context, "Uzupełnij dane", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void executeData() {
        type.requestFocus();

    }
}
