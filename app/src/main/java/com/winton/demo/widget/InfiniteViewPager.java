package com.winton.demo.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author: winton
 * @time: 2018/9/6 上午10:38
 * @desc: 描述
 */
public class InfiniteViewPager extends ViewPager {

    public InfiniteViewPager(Context context) {
        super(context);
    }

    public InfiniteViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        int position = getCurrentItem();
        if(position < 0  || position >childCount-1){
            //异常情况不处理
            return i;
        }
        if(i>=position){
            return childCount-1-(i-position);
        }
        if(i<position){
            return position-i-1;
        }
        return super.getChildDrawingOrder(childCount, i);
    }

    float startX, startY;
    int dragPos  =0;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                dragPos = getCurrentItem();
                break;
            case MotionEvent.ACTION_MOVE:
                dealMove(ev);

            break;
            case MotionEvent.ACTION_UP:
                View vi  = getChildAt(dragPos);
                vi.setTranslationX(0);
                vi.setTranslationY(0);
                int newPos = dragPos +1;
                if(newPos >= getChildCount()){
                    newPos = 0;
                }
                setCurrentItem(newPos);
            break;
        }

        return true;
    }

    private void dealMove(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();

        float dx = x -startX;
        float dy = y - startY;


        View page  = getChildAt(dragPos);
        float transx = page.getTranslationX()+dx;
        float transy = page.getTranslationY()+dy;

        page.setTranslationX(transx);
        page.setTranslationY(transy);
        startX = x;
        startY = y;

    }


}
