package com.bula.Wallet.app.Core;

import android.graphics.Paint;

import java.math.BigDecimal;

/**
 * Created by Krzysiek on 2014-08-26.
 */
public class FillDiagram {
    Paint _paint;
    float _fillPercent;
    String _type;
    float _cost;

    public  FillDiagram(int color, float fillPercent, String type, float cost)
    {
        _paint =new Paint();
        _paint.setColor(color);
        _paint.setAntiAlias(true);
        _paint.setStyle(Paint.Style.FILL);
        _fillPercent = fillPercent;
        _type = type;
        _cost = cost;
    }

    public Paint getPaint()
    {
        return _paint;
    }

    public String getTypeName()
    {
        return _type;
    }

    public float getTypeCost()
    {
        return _cost;
    }

    public float getFillPercent()
    {
        return _fillPercent;
    }

    public float getDegree()
    {
        BigDecimal decimal = new BigDecimal(360*_fillPercent);
        decimal = decimal.setScale(2,BigDecimal.ROUND_HALF_UP);
        return decimal.floatValue();
    }
}
