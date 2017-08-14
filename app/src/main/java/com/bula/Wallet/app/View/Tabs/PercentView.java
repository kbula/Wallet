package com.bula.Wallet.app.View.Tabs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.bula.Wallet.app.Core.BasicHelper;
import com.bula.Wallet.app.Core.Data.TypeData;
import com.bula.Wallet.app.Core.DataBase.DataBaseConnection;
import com.bula.Wallet.app.Core.DataBase.DataBaseHelper;
import com.bula.Wallet.app.Core.FillDiagram;
import com.bula.Wallet.app.Core.Data.IntervalDateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysiek on 2014-08-26.
 */
public class PercentView extends View {

    private boolean dialog = false;

    public PercentView (Context context) {
        super(context);
        init();
    }

    public PercentView (Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PercentView (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        bgpaint = new Paint();
        bgpaint.setColor(Color.BLACK);
        bgpaint.setAntiAlias(true);
        bgpaint.setStyle(Paint.Style.FILL);
        rect = new RectF();
    }

    Paint paint;
    Paint bgpaint;
    RectF rect;
    float percentage = 20;
    List<FillDiagram> _listFillDiagram;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //draw background circle anyway
        if(dialog) {
            showOnDialog(canvas);
            return;
        }
        show(canvas);
    }
    public void setPercentage(float percentage) {
        this.percentage = percentage / 100;
        invalidate();
    }

    public void fill(IntervalDateTime intervalDateTime)
    {
        dialog = false;
        _listFillDiagram = fillData(intervalDateTime);
        invalidate();
    }

    public void fillDialog(IntervalDateTime intervalDateTime)
    {
        dialog = true;
        _listFillDiagram = fillData(intervalDateTime);
        invalidate();
    }

    private List<FillDiagram> fillData(IntervalDateTime dateTimeHelper)
    {
        DataBaseHelper db = DataBaseConnection.getConnection(getContext());
        float totalCost =  Float.parseFloat(db.getSumCost(dateTimeHelper));
        float typeCost;

        List<FillDiagram> listFillDiagram = new ArrayList<FillDiagram>();
        List<TypeData> listType = db.getAllTypesWithColor();
        for(int i=1; i<= Integer.parseInt(db.getCountType()); i++)
        {
            typeCost = Float.parseFloat(db.getSumCostTypeInterval(i,dateTimeHelper));
            if(typeCost != 0)
            {
                listFillDiagram.add(new FillDiagram(listType.get(i-1).getColor(), (typeCost/totalCost), listType.get(i-1).getName(), typeCost));
            }else
            {
                listFillDiagram.add(new FillDiagram(listType.get(i-1).getColor(), 0, listType.get(i-1).getName(), 0));
            }
        }
        return listFillDiagram;
    }

    public void show(Canvas canvas)
    {
        int left = 10;
        int width = getWidth();
        int height = getHeight();
        int top = 0;
        if(width < height + 150) {
            rect.set(left, top, (left + height) / 2, (top + height) / 2);
            drawLegend(canvas, height, (left + height) / 2 +10, false);
        }else
        {
            rect.set(left, top,  (left + height) / 2 - 80,  (left + height) / 2 - 80);
            drawLegend(canvas, width/2, height, false);
        }
    }

    public void showOnDialog(Canvas canvas)
    {
        int left = 10;
        int width = getWidth();
        int height = getHeight();
        int top = 10;

        if(width < height + 150) {
            rect.set(left, top, (left + height) / 2, (top + height) / 2);
            drawLegend(canvas, width, left, true);
        }else
        {
            rect.set(left, top,  height - 120,  height - 120);
            drawLegend(canvas, height, height - 120, false);
        }
    }

    private void drawLegend(Canvas canvas, int width, int startDrawFromLeft, boolean drawBelow)
    {
        int i = width;
        int startDraw = startDrawFromLeft;
        int rectangleSize= width/25;
        float count = 0.0f;

        if(!drawBelow)
            i = 100;

        if(_listFillDiagram!= null) {
            float lastDegree = 0;
            for (FillDiagram item : _listFillDiagram) {
                canvas.drawArc(rect, lastDegree, item.getDegree(), true, item.getPaint());
                lastDegree += item.getDegree();

                canvas.drawRect(startDraw + 20, i, startDraw + rectangleSize + 20, rectangleSize + i, item.getPaint());
                item.getPaint().setTextSize(25);
                i = i + rectangleSize + 2;

                canvas.drawText(item.getTypeName() + " " + item.getTypeCost(), startDraw + rectangleSize + 35, i - 15, item.getPaint());
                count += item.getTypeCost();
            }
            i = i + +2;
            paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setFakeBoldText(true);
            paint.setTextSize(25);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawLine(startDraw, i, startDraw + 300, i, paint);
            i = i + rectangleSize + 12;
            canvas.drawText(String.format("Suma : %.2f", count), startDraw + 40, i, paint);
        }
    }

}
