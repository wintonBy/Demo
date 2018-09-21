package com.winton.demo.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.winton.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: winton
 * @time: 2018/9/5 下午8:42
 * @desc: 描述
 */
public class CardAdapter extends PagerAdapter {

    private Context mContext;
    private List<Integer> mData;
    private List<Integer> showData;

    public CardAdapter(Context mContext, List<Integer> mData) {
        this.mContext = mContext;
        this.mData = mData;
        initShowData();
    }

    private void initShowData(){
        showData = new ArrayList<>();
        showData.addAll(mData);
        showData.add(0,mData.get(mData.size()-1));
        showData.add(mData.get(0));
    }

    @Override
    public int getCount() {
        return mData == null? 0:mData.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_card,null);
        ImageView img = view.findViewById(R.id.img);
        img.setImageResource(mData.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeViewAt(position);
    }
}
