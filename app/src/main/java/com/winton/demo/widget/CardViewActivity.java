package com.winton.demo.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.winton.demo.BaseActivity;
import com.winton.demo.R;
import com.winton.demo.adapter.CardAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: winton
 * @time: 2018/9/5 下午8:40
 * @desc: 描述
 */
public class CardViewActivity extends BaseActivity {

    ViewPager mVP;
    private List<Integer> mImages = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_card);
        mImages.add(R.mipmap.img1);
        mImages.add(R.mipmap.img2);
        mImages.add(R.mipmap.img3);



        mVP = findViewById(R.id.vp);
        mVP.setOffscreenPageLimit(3);
        mVP.setPageMargin(10);
        mVP.setPageTransformer(false,new CardViewTrasnformer());
        mVP.setAdapter(new CardAdapter(this,mImages));
    }
}
