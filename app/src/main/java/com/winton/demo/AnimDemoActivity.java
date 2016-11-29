package com.winton.demo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by winton on 2016/11/18.
 */

public class AnimDemoActivity extends BaseActivity implements View.OnClickListener{
    private ImageView mIVAnim;
    private Button mBtScale;
    private Button mBtAlpha;
    private Button mBtRotate;

    private Animation scaleAnimation;
    private Animation alphaAnimation;
    private Animation rotateAnimation;

    private ScaleAnimation mScaleAnimation;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_demo);
        mIVAnim = (ImageView)findViewById(R.id.iv_anim);
        mBtScale = (Button) findViewById(R.id.bt_anim_scale);
        mBtAlpha = (Button)findViewById(R.id.bt_anim_alpha);
        mBtRotate = (Button)findViewById(R.id.bt_anim_rotate) ;
        initData();
        initListener();
    }

    private void initData(){
        scaleAnimation = AnimationUtils.loadAnimation(AnimDemoActivity.this,R.anim.scale);
        alphaAnimation = AnimationUtils.loadAnimation(AnimDemoActivity.this,R.anim.alpha);
        rotateAnimation = AnimationUtils.loadAnimation(AnimDemoActivity.this,R.anim.rotate);

        /*code*/
        mScaleAnimation = new ScaleAnimation(0,1,0,1);
        mScaleAnimation.setDuration(2000);
        mScaleAnimation.setRepeatCount(4);
        mScaleAnimation.setRepeatMode(Animation.REVERSE);

    }
    private void initListener(){
        mBtScale.setOnClickListener(this);
        mBtAlpha.setOnClickListener(this);
        mBtRotate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == mBtScale){
            mIVAnim.startAnimation(scaleAnimation);
//            mIVAnim.startAnimation(mScaleAnimation);
            return;
        }
        if(view == mBtAlpha){
            mIVAnim.clearAnimation();
            mIVAnim.startAnimation(alphaAnimation);
        }
        if(view == mBtRotate){
            mIVAnim.clearAnimation();
            mIVAnim.startAnimation(rotateAnimation);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
