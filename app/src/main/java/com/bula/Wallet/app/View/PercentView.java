package com.bula.Wallet.app.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.bula.Wallet.app.Data.FillDiagram;

import java.util.List;

/**
 * Created by Krzysiek on 2014-08-26.
 */
public class PercentView extends View {

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
        int left = 10;
        int width = getWidth();
        int height = getHeight();
        int top = 0;
        if(width < height + 150) {
            rect.set(left, top, (left + height) / 2, (top + height) / 2);
        }else
        {
            rect.set(left, top,  height - 40,  height - 40);
        }
       // canvas.drawArc(rect, -90, 360, true, bgpaint);
   /*     if(percentage!=0) {
            canvas.drawArc(rect, -90, (360*percentage), true, paint);
        }*/

        int i =0;
        int startDraw = (left+width)/2;
        int rectangeSize= width/29;

        if(_listFillDiagram!= null)
        {
            float lastDegree=0;
            for(FillDiagram item : _listFillDiagram)
            {
                canvas.drawArc(rect, lastDegree, item.getDegree(), true, item.getPaint());
                lastDegree +=item.getDegree();

                canvas.drawRect(startDraw+20, i, startDraw+rectangeSize+20, rectangeSize+i, item.getPaint());
                item.getPaint().setTextSize(20);
                i = i + rectangeSize+2;

                canvas.drawText(item.getTypeName() + " " + item.getTypeCost(), startDraw + rectangeSize + 30, i - 10, item.getPaint());
            }
        }
    }
    public void setPercentage(float percentage) {
        this.percentage = percentage / 100;
        invalidate();
    }

    public void fill(List<FillDiagram> listFillDiagram)
    {
        _listFillDiagram =listFillDiagram;
        invalidate();
    }

}
