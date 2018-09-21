package com.winton.demo.widget;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.RotateAnimation;

/**
 * @author: winton
 * @time: 2018/9/5 下午8:00
 * @desc: 描述
 */
public class CardViewTrasnformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View page, float position) {
        Log.d("winton","page"+page.toString()+":"+position);
        page.setTranslationX(-position * page.getWidth());
        page.setScaleX(0.8f);
        page.setScaleY(0.8f);
        if(position != 0){
            page.setRotation(10);
        }else {
            page.setRotation(0);
        }
    }


}
