package com.winton.demo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winton.demo.DemoBean;
import com.winton.demo.R;

import java.util.List;

/**
 * Created by winton on 2016/11/18.
 */

public class DemoBeanAdapter extends BaseAdapter<DemoBean> {
    public DemoBeanAdapter(List<DemoBean> list, Context context) {
        super(list, context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tvName ;
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.item_demo_bean,parent,false);
            tvName = (TextView)convertView.findViewById(R.id.tv_item_name);
            convertView.setTag(tvName);
        }else {
            tvName = (TextView)convertView.getTag();
        }
        DemoBean item = sourceList.get(position);
        tvName.setText(item.getName());

        return convertView;
    }
}
