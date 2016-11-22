package com.winton.demo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by winton on 2016/11/18.
 */

public class AnimDemoActivity extends BaseActivity implements View.OnClickListener{
    private ImageView mIVAnim;
    private Button mBtScale;

    private Animation scaleAnimation;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_demo);
        mIVAnim = (ImageView)findViewById(R.id.iv_anim);
        mBtScale = (Button) findViewById(R.id.bt_anim_scale);
        initData();
        initListener();
    }

    private void initData(){
        scaleAnimation = AnimationUtils.loadAnimation(AnimDemoActivity.this,R.anim.scale);
    }
    private void initListener(){
        mBtScale.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == mBtScale){
            mIVAnim.startAnimation(scaleAnimation);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
