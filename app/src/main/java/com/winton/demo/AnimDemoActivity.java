package com.winton.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
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
    private Button mBtTranslate;

    private Animation scaleAnimation;
    private Animation alphaAnimation;
    private Animation rotateAnimation;
    private Animation translateAnimation;

    private ScaleAnimation mScaleAnimation;
    private AlphaAnimation mAlphaAnimation;
    private RotateAnimation mRotateAnimation;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_demo);
        mIVAnim = (ImageView)findViewById(R.id.iv_anim);
        mBtScale = (Button) findViewById(R.id.bt_anim_scale);
        mBtAlpha = (Button)findViewById(R.id.bt_anim_alpha);
        mBtRotate = (Button)findViewById(R.id.bt_anim_rotate) ;
        mBtTranslate = (Button)findViewById(R.id.bt_anim_translate);
        initData();
        initListener();
    }

    private void initData(){
        scaleAnimation = AnimationUtils.loadAnimation(AnimDemoActivity.this,R.anim.scale);
        alphaAnimation = AnimationUtils.loadAnimation(AnimDemoActivity.this,R.anim.alpha);
        rotateAnimation = AnimationUtils.loadAnimation(AnimDemoActivity.this,R.anim.rotate);
        translateAnimation = AnimationUtils.loadAnimation(AnimDemoActivity.this,R.anim.translate);

        /*code*/
        mScaleAnimation = new ScaleAnimation(0,1,0,1);
        mScaleAnimation.setDuration(2000);
        mScaleAnimation.setRepeatCount(4);
        mScaleAnimation.setRepeatMode(Animation.REVERSE);
        mAlphaAnimation = new AlphaAnimation(0,1);
        mAlphaAnimation.setDuration(2000);
        mAlphaAnimation.setRepeatMode(Animation.REVERSE);
        mAlphaAnimation.setRepeatCount(5);
        mRotateAnimation = new RotateAnimation(0,90,50,50);
        mRotateAnimation.setDuration(2000);
        mRotateAnimation.setRepeatCount(4);
        mRotateAnimation.setRepeatMode(Animation.REVERSE);
        mRotateAnimation.setInterpolator(this,android.R.anim.accelerate_interpolator);

    }
    private void initListener(){
        mBtScale.setOnClickListener(this);
        mBtAlpha.setOnClickListener(this);
        mBtRotate.setOnClickListener(this);
        mBtTranslate.setOnClickListener(this);
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
//            mIVAnim.startAnimation(alphaAnimation);
            mIVAnim.startAnimation(mAlphaAnimation);
        }

        if(view == mBtRotate){
            mIVAnim.clearAnimation();
            mIVAnim.startAnimation(rotateAnimation);
//            mIVAnim.startAnimation(mRotateAnimation);
        }
        if(view == mBtTranslate){
            mIVAnim.clearAnimation();
            mIVAnim.startAnimation(translateAnimation);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
