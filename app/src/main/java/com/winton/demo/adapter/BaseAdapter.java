package com.winton.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by winton on 2016/11/3.
 */

public abstract class BaseAdapter<T> extends android.widget.BaseAdapter{

    public List<T> sourceList ;
    public Context mContext;
    public LayoutInflater mLayoutInflater;

    public BaseAdapter(List<T> list, Context context){
        sourceList = list;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public List<T> getSource(){
        return  sourceList;
    }

    public void addData(List<T> data){
        if(sourceList == null){
            sourceList = new ArrayList<>();
        }
        sourceList.addAll(data);
        notifyDataSetChanged();
    }
    public void updateData(List<T> data){
        sourceList = new ArrayList<>();
        sourceList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(sourceList != null){
            return sourceList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(sourceList != null){
            return sourceList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    abstract public View getView(int position, View convertView, ViewGroup parent);
}
