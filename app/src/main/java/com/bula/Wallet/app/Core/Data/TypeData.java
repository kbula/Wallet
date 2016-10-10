package com.bula.Wallet.app.Core.Data;

/**
 * Created by Krzysiek on 2016-01-01.
 */
public class TypeData {
    String _name;
    int _color;

    public TypeData(String name, int color)
    {
        _name = name;
        _color = color;
    }

    public String getName()
    {
        return _name;
    }

    public int getColor()
    {
        return _color;
    }
}
