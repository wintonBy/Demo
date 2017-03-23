package com.winton.demo.valueanimatortest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.winton.demo.R;

/**
 * Created by winton on 2017/1/15.
 */

public class SplashView extends View{

    private int[] pointColors =new int[]{R.color.red,R.color.orange,R.color.yellow,R.color.green,R.color.cyan,R.color.blue,R.color.purple};

    private ValueAnimator valueAnimator ;
    private SplashState mState;

    private int centerX;
    private int centerY;
    private int viewDi;
    private float currentAngle;
    private int currentRadius;
    private int currentStokeWidth;

    private static final int START_RADIUS = 120; //开始时半径
    private static final int SMALL_CIRCLE_RADIUS = 20;
    private static final int CIRCLE_NUMBER = 7; //小球数量


    private Paint mPaint;

    public SplashView(Context context) {
        this(context,null);
    }

    public SplashView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        centerX = getMeasuredWidth()/2;
        centerY = getMeasuredHeight()/2;

        viewDi = (int)Math.sqrt(centerX*centerX +centerY*centerY);
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mState == null){
            mState = new RotateState();
        }
        mState.doDraw(canvas);
       super.onDraw(canvas);
    }

    public void doLoadOver(){
        mState = new ShrinkState();
        invalidate();
    }

    /*一阶段旋转*/
    private class RotateState implements SplashState{
        private  int ANIMATOR_DURATION = 2000; //动画持续时间
        public RotateState() {
            currentRadius = START_RADIUS;
            valueAnimator = ValueAnimator.ofFloat(0,(float)Math.PI*2);
            valueAnimator.setDuration(ANIMATOR_DURATION);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float value = (float)valueAnimator.getAnimatedValue();
                    currentAngle = (float)(360/2*Math.PI) *value;
                }
            });
            valueAnimator.start();
        }
        @Override
        public void doDraw(Canvas mCanvas) {
            drawBackground(mCanvas);
            drawCircle(mCanvas);
            invalidate();
        }
    }
    /*二阶段收缩*/
    private class ShrinkState implements SplashState{
        private  int ANIMATOR_DURATION = 1200; //动画持续时间
        public ShrinkState() {
            if(mState != null && mState instanceof RotateState){
                valueAnimator.cancel();
                valueAnimator = null;
            }
            valueAnimator = ValueAnimator.ofInt(START_RADIUS,0);
            valueAnimator.setDuration(ANIMATOR_DURATION);
            valueAnimator.setInterpolator(new OvershootInterpolator(10));
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    currentRadius = (int)valueAnimator.getAnimatedValue();
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mState = new ExtendState();
                }
            } );
            valueAnimator.start();
        }

        @Override
        public void doDraw(Canvas mCanvas) {
            drawBackground(mCanvas);
            drawCircle(mCanvas);
            invalidate();
        }
    }
    /*三阶段扩展*/
    private class ExtendState implements SplashState{
        private  int ANIMATOR_DURATION = 1000; //动画持续时间
            public ExtendState(){
            if(mState != null && mState instanceof ShrinkState){
                valueAnimator.cancel();
                valueAnimator = null;
            }
            currentStokeWidth = 2*viewDi-START_RADIUS;
            valueAnimator = ValueAnimator.ofInt(2*viewDi-START_RADIUS,0);
            valueAnimator.setDuration(ANIMATOR_DURATION);
            valueAnimator.setInterpolator(new AccelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    currentStokeWidth = (int)valueAnimator.getAnimatedValue();

                }
            });
            valueAnimator.start();
        }

        @Override
        public void doDraw(Canvas mCanvas) {
            clearBackground(mCanvas);
            drawExpandOver(mCanvas);
            invalidate();
        }
    }

    /**
     * 画小圆
     * @param canvas
     */
    private void drawCircle(Canvas canvas){
        for(int i=0;i<CIRCLE_NUMBER ;i++){
            float angle = (360/CIRCLE_NUMBER) * i + currentAngle ;
            float x = (float)(centerX + Math.cos(angle*Math.PI/180)*currentRadius);
            float y = (float)(centerY + Math.sin(angle*Math.PI/180)*currentRadius);
            mPaint.setColor(getResources().getColor(pointColors[i]));
            canvas.drawCircle(x,y,SMALL_CIRCLE_RADIUS,mPaint);
        }
    }

    /**
     * 画背景
     * @param canvas
     */
    private void drawBackground(Canvas canvas){
        canvas.drawARGB(255,255,255,255);
    }

    /**
     * 画透明背景
     * @param canvas
     */
    private void clearBackground(Canvas canvas){
        canvas.drawARGB(0,0,0,0);
    }

    /**
     * 或扩展圆环
     * @param canvas
     */
    private void drawExpandOver(Canvas canvas){
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(currentStokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(centerX,centerY,viewDi,mPaint);
    }

}
