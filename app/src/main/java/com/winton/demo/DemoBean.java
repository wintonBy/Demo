package com.winton.demo;

import android.content.Intent;

/**
 * Created by winton on 2016/11/18.
 */

public class DemoBean {

    String name;
    Intent mIntent ;

    public DemoBean(String name, Intent mIntent) {
        this.name = name;
        this.mIntent = mIntent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Intent getmIntent() {
        return mIntent;
    }

    public void setmIntent(Intent mIntent) {
        this.mIntent = mIntent;
    }
}
