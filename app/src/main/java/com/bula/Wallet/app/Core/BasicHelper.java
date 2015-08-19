package com.bula.Wallet.app.Core;

import android.content.Context;
import android.graphics.Color;

import com.bula.Wallet.app.Core.Data.IntervalDateTime;
import com.bula.Wallet.app.R;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Krzysiek on 2014-08-09.
 */
public class BasicHelper {

    public static String getDateToday()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getDate(Date date)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(date);
    }

    public static String getTime(Date date)
    {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(date);
    }

    public static String getDateTimeNow()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getDateTime(Date date)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public static String getTimeNow()
    {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static Date stringToDate(String date)
    {
        try {
            return new SimpleDateFormat("yyyy/MM/dd").parse(date);
        }catch (Exception ex)
        {
        }
        return null;
    }

    public static Date stringToDateTime(String time)
    {
        try {
            return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(time);
        }catch (Exception ex)
        {
        }
        return new Date();
    }

    public static IntervalDateTime getThisWeek()
    {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - calendar.getFirstDayOfWeek();
        calendar.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
        calendar = setTime(calendar,true);
        Date weekStart = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 6);
        calendar = setTime(calendar,false);
        Date weekEnd = calendar.getTime();

        return new IntervalDateTime(weekStart,weekEnd);
    }

    public static IntervalDateTime getLastWeek()
    {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - calendar.getFirstDayOfWeek();
        calendar.add(Calendar.DAY_OF_MONTH, -dayOfWeek-7);
        calendar = setTime(calendar,true);
        Date weekStart = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 6);
        calendar = setTime(calendar,false);
        Date weekEnd = calendar.getTime();

        return new IntervalDateTime(weekStart,weekEnd);
    }

    public static IntervalDateTime getThisMonth()
    {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar = setTime(calendar,true);
        Date startMonth = calendar.getTime();

        calendar.setTime(date);
        calendar.add(Calendar.MONTH,1);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.add(Calendar.DATE,-1);
        calendar = setTime(calendar,false);
        Date endMonth = calendar.getTime();

        return new IntervalDateTime(startMonth,endMonth);
    }

    public static IntervalDateTime getLatsMonth()
    {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_MONTH,0);
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,-1);
        if(calendar.getTime().getMonth()== date.getMonth())
        {
            calendar.add(Calendar.MONTH,-1);
        }
        calendar.set(Calendar.DAY_OF_MONTH,0);
        calendar = setTime(calendar,true);
        Date startMonth = calendar.getTime();

        calendar.setTime(date);
        calendar.add(Calendar.MONTH,0);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.add(Calendar.DATE,-1);
        calendar = setTime(calendar,false);
        Date endMonth = calendar.getTime();

        return new IntervalDateTime(startMonth,endMonth);
    }

    public static IntervalDateTime getSelectedMonth(Calendar calendar)
    {
        Date dateBegin,dateEnd ;

        calendar.set(Calendar.DAY_OF_MONTH,1);
        dateBegin = setTime(calendar,true).getTime();
        calendar.set(Calendar.DAY_OF_MONTH , calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        dateEnd = setTime(calendar,false).getTime();

        return new IntervalDateTime(dateBegin,dateEnd);
    }

    public static List<IntervalDateTime> getAllWeekInMonth(int monthNumber , int year)
    {
        List<IntervalDateTime> intervalDateTimes = new ArrayList<IntervalDateTime>();
        Date dateBegin,dateEnd ;
        int dayOfWeek;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH,monthNumber);
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_MONTH,0);

        for(int i=0; i < 6; i++) {
            dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - calendar.getFirstDayOfWeek();
            calendar.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
            calendar = setTime(calendar, true);
            dateBegin = calendar.getTime();

            calendar.add(Calendar.DAY_OF_MONTH, 6);
            calendar = setTime(calendar, false);
            dateEnd = calendar.getTime();
            if(dateBegin.getMonth() == monthNumber || dateBegin.getMonth() == (monthNumber-1)) {
                intervalDateTimes.add(new IntervalDateTime(dateBegin, dateEnd));
                calendar.setTime(dateEnd);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        }

        return intervalDateTimes;
    }

    public static String getMonthName(int num, Context _context)
    {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = createMonthTable(_context);//dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }

    private static Calendar setTime(Calendar calendar,boolean begin)
    {
        if(begin) {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
        }else
        {
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
        }

        return calendar;
    }

    public static int[] createColorTable(Context _context)
    {
        int[] colorTable = {TypeColor.EAT.getColor(_context),
                TypeColor.FLAT.getColor(_context),
                TypeColor.HEALTHY.getColor(_context),
                TypeColor.TRANSPORT.getColor(_context) ,
                TypeColor.CLOTHES.getColor(_context),
                TypeColor.RELAX.getColor(_context),
                TypeColor.OTHER.getColor(_context),
                Color.DKGRAY};
        return colorTable;
    }

    public static String[] createMonthTable(Context _context)
    {
        String[] monthTable = {_context.getResources().getString(R.string.January),
                _context.getResources().getString(R.string.February),
                _context.getResources().getString(R.string.March),
                _context.getResources().getString(R.string.April),
                _context.getResources().getString(R.string.May),
                _context.getResources().getString(R.string.June),
                _context.getResources().getString(R.string.July),
                _context.getResources().getString(R.string.August),
                _context.getResources().getString(R.string.September),
                _context.getResources().getString(R.string.October),
                _context.getResources().getString(R.string.November),
                _context.getResources().getString(R.string.December)};
        return monthTable;
    }

}
