package com.winton.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
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
    private Button mBtSet;

    private Animation scaleAnimation;
    private Animation alphaAnimation;
    private Animation rotateAnimation;
    private Animation translateAnimation;
    private Animation setAnimation;

    private ScaleAnimation mScaleAnimation;
    private AlphaAnimation mAlphaAnimation;
    private RotateAnimation mRotateAnimation;
    private TranslateAnimation mTranslateAnimation;
    private AnimationSet mSetAnimation;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_demo);
        mIVAnim = (ImageView)findViewById(R.id.iv_anim);
        mBtScale = (Button) findViewById(R.id.bt_anim_scale);
        mBtAlpha = (Button)findViewById(R.id.bt_anim_alpha);
        mBtRotate = (Button)findViewById(R.id.bt_anim_rotate) ;
        mBtTranslate = (Button)findViewById(R.id.bt_anim_translate);
        mBtSet = (Button)findViewById(R.id.bt_anim_set);
        initData();
        initListener();
    }

    private void initData(){
        scaleAnimation = AnimationUtils.loadAnimation(AnimDemoActivity.this,R.anim.scale);
        alphaAnimation = AnimationUtils.loadAnimation(AnimDemoActivity.this,R.anim.alpha);
        rotateAnimation = AnimationUtils.loadAnimation(AnimDemoActivity.this,R.anim.rotate);
        translateAnimation = AnimationUtils.loadAnimation(AnimDemoActivity.this,R.anim.translate);
        setAnimation = AnimationUtils.loadAnimation(AnimDemoActivity.this,R.anim.set);

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

        mTranslateAnimation = new TranslateAnimation(0f,200f,0f,100f);
        mTranslateAnimation.setDuration(2000);
        mTranslateAnimation.setRepeatCount(4);
        mTranslateAnimation.setRepeatMode(Animation.RESTART);
        mTranslateAnimation.setInterpolator(this,android.R.anim.bounce_interpolator);

        mSetAnimation = new AnimationSet(this,null);
        mSetAnimation.setRepeatMode(Animation.RESTART);
        mSetAnimation.setDuration(500);
        mSetAnimation.addAnimation(mScaleAnimation);
        mSetAnimation.addAnimation(mAlphaAnimation);
        mSetAnimation.addAnimation(mRotateAnimation);
        mSetAnimation.addAnimation(mTranslateAnimation);

    }
    private void initListener(){
        mBtScale.setOnClickListener(this);
        mBtAlpha.setOnClickListener(this);
        mBtRotate.setOnClickListener(this);
        mBtTranslate.setOnClickListener(this);
        mBtSet.setOnClickListener(this);
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
//            mIVAnim.startAnimation(translateAnimation);
            mIVAnim.startAnimation(mTranslateAnimation);
        }
        if(view == mBtSet){
            mIVAnim.clearAnimation();
            mIVAnim.startAnimation(setAnimation);
//            mIVAnim.startAnimation(mSetAnimation);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
