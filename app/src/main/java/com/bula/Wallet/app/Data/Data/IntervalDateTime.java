package com.bula.Wallet.app.Data.Data;

import com.bula.Wallet.app.Data.BasicHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * Created by Krzysiek on 2014-08-09.
 */
public class IntervalDateTime {
    private Date _begin;
    private Date _end;

    public IntervalDateTime (Date begin, Date end)
    {
        _begin = begin;
        _end = end;
    }

    public IntervalDateTime (String begin, String end)
    {
        SimpleDateFormat froamter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            _begin = froamter.parse(begin+" 00:00:00");
            _end = froamter.parse(end+" 23:59:59");
        }catch (Exception ex)
        {

        }
    }

    public Date getBegin() {return _begin;}
    public Date getEnd() {return _end;}

    public String getBeginDate()
    {
        return BasicHelper.getDate(_begin);
    }

    public String getBeginDateTime()
    {
        return BasicHelper.getDateTime(_begin);
    }

    public String getEndDate()
    {
        return BasicHelper.getDate(_end);
    }

    public String getEndDateTime()
    {
        return BasicHelper.getDateTime(_end);
    }



}
