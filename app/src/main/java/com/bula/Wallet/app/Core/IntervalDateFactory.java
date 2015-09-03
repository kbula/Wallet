package com.bula.Wallet.app.Core;

import com.bula.Wallet.app.Core.Data.IntervalDateTime;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Krzysiek on 2015-08-20.
 */
public class IntervalDateFactory {

    public static IntervalDateTime getIntervalDate(Intervals intervals)
    {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        switch (intervals) {
            case ThisWeek:
                return getThisWeek(calendar);
            case LastWeek:
                return getLastWeek(calendar);
            case ThisMonth:
                return getThisMonth(calendar);
            case LastMonth:
                return getLastMonth(calendar, date);
        }
        return new IntervalDateTime(date, date);
    }

    private static IntervalDateTime getThisMonth(Calendar calendar)
    {
        Date intervalStart;
        Date intervalEnd;

        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar = BasicHelper.setTime(calendar, true);
        intervalStart = calendar.getTime();

        calendar.add(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)-1);
        calendar = BasicHelper.setTime(calendar, false);
        intervalEnd = calendar.getTime();

        return new IntervalDateTime(intervalStart,intervalEnd);
    }

    private static IntervalDateTime getLastMonth(Calendar calendar, Date date)
    {
        Date intervalStart;
        Date intervalEnd;

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, -1);
        if(calendar.getTime().getMonth()== date.getMonth())
        {
            calendar.add(Calendar.MONTH,-1);
        }
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar = BasicHelper.setTime(calendar, true);
        intervalStart = calendar.getTime();

        calendar.add(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)-1);
        calendar = BasicHelper.setTime(calendar, false);
        intervalEnd = calendar.getTime();

        return new IntervalDateTime(intervalStart,intervalEnd);
    }

    private static IntervalDateTime getThisWeek(Calendar calendar)
    {
        int dayOfWeek;
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - calendar.getFirstDayOfWeek();
        calendar.add(Calendar.DAY_OF_MONTH, -dayOfWeek);

        return getEndAndStartDateWeek(calendar);
    }

    private static IntervalDateTime getLastWeek(Calendar calendar)
    {
        int dayOfWeek;
        int week = 7;
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - calendar.getFirstDayOfWeek();
        calendar.add(Calendar.DAY_OF_MONTH, - dayOfWeek - week);

        return getEndAndStartDateWeek(calendar);
    }

    private static IntervalDateTime getEndAndStartDateWeek(Calendar calendar)
    {
        Date intervalStart;
        Date intervalEnd;

        calendar = BasicHelper.setTime(calendar, true);
        intervalStart = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 6);
        calendar = BasicHelper.setTime(calendar, false);
        intervalEnd = calendar.getTime();

        return new IntervalDateTime(intervalStart,intervalEnd);
    }

}
