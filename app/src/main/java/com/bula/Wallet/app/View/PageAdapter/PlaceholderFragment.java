
package com.bula.Wallet.app.View.PageAdapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bula.Wallet.app.Core.DataBase.DataBaseConnection;
import com.bula.Wallet.app.Core.DataBase.DataBaseHelper;
import com.bula.Wallet.app.R;
import com.bula.Wallet.app.View.Tabs.AllView;
import com.bula.Wallet.app.View.Tabs.IView;
import com.bula.Wallet.app.View.Tabs.MainView;
import com.bula.Wallet.app.View.Tabs.StatisticViewNew;
import com.bula.Wallet.app.View.Tabs.StatisticsView;


/**
 * A placeholder fragment containing a simple view.
 */

public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    DataBaseHelper db;
    Context _context;
    IView fragmentView;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber){
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public PlaceholderFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        db = DataBaseConnection.getConnection(container.getContext());
        _context= container.getContext();

        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 1:

                rootView = inflater.inflate(R.layout.fragment_main_view, container, false);
                fragmentView = new MainView( _context, db);
                fragmentView.createView(rootView);
                fragmentView.executeData();
                break;
            case 2:
                rootView = inflater.inflate(R.layout.statistics_new_view, container, false);
                fragmentView = new StatisticViewNew(_context, db);
                fragmentView.createView(rootView);
                fragmentView.executeData();
                break;
            case 3:
                rootView = inflater.inflate(R.layout.all_view, container, false);
                fragmentView = new AllView(_context, db);
                fragmentView.createView(rootView);
                fragmentView.executeData();
                break;
            case 4:
                rootView = inflater.inflate(R.layout.statistics_view, container, false);
                fragmentView = new StatisticsView(_context, db);
                fragmentView.createView(rootView);
                fragmentView.executeData();
                break;
            default:
                rootView = inflater.inflate(R.layout.fragment_main_view, container, false);
                break;
        }

        return rootView;
    }

    public IView getActualView()
    {
        return fragmentView;
    }

}