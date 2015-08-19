package com.bula.Wallet.app.Core.Data;


/**
 * Created by Krzysiek on 2014-08-03.
 */
public class WalletData {
    Double _cost;
    String _type;
    String _comment;
    String _date;
    Integer _id;

    public WalletData(Integer id,Double cost,String type, String comment, String dateTime)
    {
        _cost= cost;
        _comment = comment;
        _date = dateTime;
        _type = type;
        _id = id;
    }

    public WalletData(Double cost, String type, String comment, String date, String time)
    {
        _cost= cost;
        _type = type;
        _comment = comment;
        _date = date+" "+time+":00";
    }

    public WalletData(Integer id,Double cost, String type, String comment, String date, String time)
    {
        _cost= cost;
        _type = type;
        _comment = comment;
        _date = date+" "+time+":00";
        _id = id;
    }

    public Double getCost()
    {
        return _cost;
    }

    public String getDate()
    {
        return _date;//BasicHelper.getDateTimeNow();
    }

    public String getType()
    {
        return _type;
    }

    public String getComment() {return _comment;}

    public Integer getId()
    {
        return _id;
    }

    @Override
    public String toString()
    {
        return _cost +" \t|\t"+ _date +" \t|\t"+ _comment;
    }
}
