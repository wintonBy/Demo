package com.winton.demo;

import android.animation.AnimatorInflater;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.winton.demo.valueanimatortest.SplashView;

/**
 * Created by winton on 2017/1/8.
 */

public class ValueAnimatorDemoActivity extends BaseActivity {

    private TextView mTVValue;
    private Button mBTValue;
    private Button mBTColor;
    private Button mBTObject;
    private SplashView mSV;


    private static Handler mUIHandler = new Handler();

    private ValueAnimator intValueAnimator;
    private ValueAnimator colorValueAnimator;
    private ValueAnimator objectValueAnimator;
    private ValueAnimator propertyHolderValueAnimator;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valueanimator);
        mTVValue = (TextView)findViewById(R.id.tv_value);
        mBTValue = (Button)findViewById(R.id.bt_value);
        mBTColor = (Button)findViewById(R.id.bt_value_color);
        mBTObject = (Button)findViewById(R.id.bt_value_object);
        mSV = (SplashView)findViewById(R.id.sv);
        initData();
        initListener();
    }
    private void initData(){
//        intValueAnimator = (ValueAnimator)AnimatorInflater.loadAnimator(this,R.animator.valueanimator);  /*xml实现了*/
        colorValueAnimator = (ValueAnimator) AnimatorInflater.loadAnimator(this,R.animator.valueanimator_rgb);
        /*代码实现*/
        intValueAnimator = ValueAnimator.ofInt(0,100);
        intValueAnimator.setDuration(10000);
        intValueAnimator.setInterpolator(new AccelerateInterpolator());

        /*代码实现对象动画*/
        objectValueAnimator = ValueAnimator.ofObject(new MyAnimObjectEvaluator(),new MyAnimObject(0),new MyAnimObject(10));
        objectValueAnimator.setDuration(10000);
        objectValueAnimator.setInterpolator(new LinearInterpolator());

        /*代码实现propertyholder*/
        propertyHolderValueAnimator = ValueAnimator.ofArgb();

        intValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
              int value = (Integer)valueAnimator.getAnimatedValue();
              mTVValue.setText(value+"");
            }
        });
        colorValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int color = (Integer)valueAnimator.getAnimatedValue();
                mTVValue.setBackgroundColor(color);

            }
        });

        objectValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                MyAnimObject value = (MyAnimObject)valueAnimator.getAnimatedValue();
                mTVValue.setText(value.value+"");

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        mUIHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSV.doLoadOver();
            }
        },3000);
    }

    private void initListener(){
        mBTValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intValueAnimator.start();
            }
        });
        mBTColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorValueAnimator.start();
            }
        });
        mBTObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                objectValueAnimator.start();
            }
        });
    }

    /*对象动画*/
    class MyAnimObject{

        int value;

        public MyAnimObject(int value){
            this.value = value;
        }
    }

    /*对象动画算值器*/
    class MyAnimObjectEvaluator implements TypeEvaluator<MyAnimObject>{

        @Override
        public MyAnimObject evaluate(float v, MyAnimObject myAnimObject, MyAnimObject t1) {
            int oldValue = myAnimObject.value;
            int newValue = t1.value;

            int res = (int)((oldValue + newValue)*v)*2;
            MyAnimObject animObject = new MyAnimObject(res);
            return animObject;
        }
    }

    class DiyInterpolator implements Interpolator{
        @Override
        public float getInterpolation(float input) {


            return input*input;  //y = x^2;
        }
    }

}
