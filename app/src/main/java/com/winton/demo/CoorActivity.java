package com.winton.demo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

/**
 * @author: winton
 * @time: 2018/8/20 15:36
 * @package: com.winton.demo
 * @project: Demo
 * @mail:
 * @describe: 一句话描述
 */
public class CoorActivity extends BaseActivity {


    private Toolbar mToolBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_coor_demo);
        mToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
