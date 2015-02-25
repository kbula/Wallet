package com.bula.Wallet.app.View.PageAdapter;

/**
 * Created by Krzysiek on 2014-08-09.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bula.Wallet.app.R;

import java.util.List;
import java.util.Locale;

/**
 * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    Context _context;
    FragmentManager fragmentManager;
    List<Fragment> fragments;

    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        _context= context;
        fragmentManager=fm;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return _context.getString(R.string.title_section1).toUpperCase(l);
            case 1:
                return _context.getString(R.string.title_section2).toUpperCase(l);
            case 2:
                return _context.getString(R.string.title_section3).toUpperCase(l);
            case 3:
                return _context.getString(R.string.title_section2).toUpperCase(l);
        }
        return null;
    }

    public void updateData(int position)
    {
        if(fragments == null)
            fragments = fragmentManager.getFragments();
        if( ((PlaceholderFragment)fragments.get(position)).getActualView()!=null)
        {
            ((PlaceholderFragment)fragments.get(position)).getActualView().executeData();
        }
    }

}