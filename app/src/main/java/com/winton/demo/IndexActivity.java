package com.winton.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.winton.demo.adapter.DemoBeanAdapter;

import java.util.ArrayList;
import java.util.List;

public class IndexActivity extends BaseActivity {
    private ListView mLVDemo;
    private List<DemoBean> mSource;
    private DemoBeanAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        mLVDemo = (ListView)findViewById(R.id.lv_demo);
        initListener();
        initData();
    }
    private void initData(){

        initDemoBeans();

        mAdapter = new DemoBeanAdapter(mSource,this);
        mLVDemo.setAdapter(mAdapter);

    }
    private void initDemoBeans(){
        mSource = new ArrayList<>();
        Intent intent = new Intent(this,AnimDemoActivity.class);
        mSource.add(new DemoBean("基本动画",intent));

        Intent intent1 = new Intent(this, InterpolatorDemoActivity.class);
        mSource.add(new DemoBean("动画插值器",intent1));

        Intent intent2 = new Intent(this, ValueAnimatorDemoActivity.class);
        mSource.add(new DemoBean("属性动画",intent2));
    }


    private void initListener(){
        mLVDemo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DemoBean demoBean = (DemoBean)mLVDemo.getAdapter().getItem(i);
                startActivity(demoBean.getmIntent());
            }
        });
    }
}
