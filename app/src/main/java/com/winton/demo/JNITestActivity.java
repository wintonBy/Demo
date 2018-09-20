package com.winton.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

/**
 * @author: winton
 * @time: 2018/8/31 下午7:49
 * @desc: 描述
 */
public class JNITestActivity extends BaseActivity{



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_jni);

        TextView textView = findViewById(R.id.tv);
        textView.setText(new JNIDemo().doNative() + new JNIUtils().getStringFromNative());
    }
}
