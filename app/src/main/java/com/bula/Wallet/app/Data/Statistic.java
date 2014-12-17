package com.bula.Wallet.app.Data;

/**
 * Created by Krzysiek on 2014-09-13.
 */
public class Statistic {
    private String _name;
    private IntervalDateTime _interval;

    public Statistic(String name, IntervalDateTime interval)
    {
        _name = name;
        _interval = interval;
    }

    public String getName() {return _name;}

    public IntervalDateTime getInterval() {return _interval;}
}
