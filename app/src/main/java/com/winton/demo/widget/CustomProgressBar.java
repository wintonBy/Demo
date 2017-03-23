package com.winton.demo.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;

/**
 * Created by winton on 2017/3/21.
 */

public class CustomProgressBar extends View {

    private int max = 100;
    private int progress = 0;
    private int mFirstColor;
    private int mSecondColor;
    private int mPointColor;
    private int mLineHeight;
    private int mRadius;
    private int mPointRadius;

    private Paint mPaint;
    private Path mPath;
    private boolean isInit;
    private boolean hasDrawFirst;
    private boolean isLoading;
    private int viewWidth;
    private int viewHeight;
    private float currentAngle = 0f;
    private ValueAnimator valueAnimator;
    private int animState = 0;
    private int padding = 20;


    private static final int DEFAULT_FIRST_COLOR = Color.parseColor("#E4E4E4");
    private static final int DEFAULT_SECOND_COLOR = Color.parseColor("#DF3B3B");
    private static final int DEFAULT_POINT_COLOR = Color.parseColor("#FEFEFE");
    private static final int DEFAULT_LINE_HEIGHT = 10;
    private static final int DEFAULT_POINT_RADIUS = 20;
    private static final int DEFAULT_SMALL_LINE_NUM = 6;
    private  int ANIMATOR_DURATION = 2000; //动画持续时间

    public CustomProgressBar(Context context) {
        this(context,null);
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();
        isInit = true;
    }

    private void init(){
        CustomProgressBarStyle style = new CustomProgressBarStyle();
        changeProgressBarStyle(style);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPath = new Path();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        if(isInit){
            drawLineBar(canvas);
            drawProgressLine(canvas);
            drawProgressPoint(canvas);
        }
    }

    private void drawLineBar(Canvas canvas){
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mFirstColor);
        mPath.reset();
        mPath.moveTo(mRadius+padding,viewHeight/2 + mLineHeight/2);
        mPath.quadTo(0+padding,viewHeight/2,mRadius+padding,viewHeight/2-mLineHeight/2);
        mPath.lineTo(viewWidth-mRadius,viewHeight/2-mLineHeight/2);
        mPath.quadTo(viewWidth,viewHeight/2,viewWidth-mRadius,viewHeight/2 + mLineHeight/2);
        mPath.close();
        canvas.drawPath(mPath,mPaint);
    }
    /**
     *
     * @param canvas
     */
    private void drawProgressPoint(Canvas canvas){
        int pointX = 0;
        if(getMax()>0){
            pointX = computerWidth(progress,max,viewWidth) +padding;
        }
        int pointY = viewHeight/2;
        mPaint.setColor(mSecondColor);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(pointX,pointY,mPointRadius+1,mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mPointColor);
        canvas.drawCircle(pointX,pointY,mPointRadius,mPaint);
        mPaint.setColor(mSecondColor);
        if(isLoading){
            //画加载转圈
            if(animState == 0){
                startLoadingAnim();
                animState =1;
            }
            drawSmallLine(canvas,pointX,pointY);
        }
        canvas.drawCircle(pointX,pointY,mLineHeight/2,mPaint);
    }
    private void drawProgressLine(Canvas canvas){
        int width = 0;
        if(getMax()>0){
            width = computerWidth(progress,max,viewWidth);
        }
        mPaint.setColor(mSecondColor);
        mPath.reset();
        mPath.moveTo(mRadius+padding,viewHeight/2 + mLineHeight/2);
        mPath.quadTo(0+padding,viewHeight/2,mRadius+padding,viewHeight/2-mLineHeight/2);
        mPath.lineTo(width,viewHeight/2-mLineHeight/2);
        mPath.lineTo(width,viewHeight/2+mLineHeight/2);
        mPath.close();
        canvas.drawPath(mPath,mPaint);
    }

    public void setProgress(int progress){
        if(progress <0 || progress >max){
            return;
        }
        this.progress = progress;
        invalidate();
    }
    public void setMax(int max){
        if(max <0 || max <progress){
            return;
        }
        this.max = max;
    }
    public void setLoading(boolean isLoading){
        this.isLoading = isLoading;
        if(animState != 0){
            animState =0;
        }
        invalidate();
    }
    public synchronized int getProgress(){
        return progress;
    }
    public synchronized int getMax(){
        return max;
    }

    private int computerWidth(float progress ,float max,float viewWidth){
        int result = (int)(progress/max * viewWidth);
        return result;
    }

    private void drawSmallLine(Canvas canvas,float centerX,float centerY){

        for(int i =0;i<DEFAULT_SMALL_LINE_NUM;i++){
            float angle = (360/DEFAULT_SMALL_LINE_NUM) * i + currentAngle ;
            mPaint.setStrokeWidth(5);
            mPaint.setColor(mSecondColor-i*80);
            float startX =(float) (centerX + Math.cos(angle*Math.PI/180)*(mLineHeight/2 + 2));
            float startY = (float) (centerY + Math.sin(angle*Math.PI/180)*(mLineHeight/2 + 2));
            float endX =(float) (centerX + Math.cos(angle*Math.PI/180)*(mLineHeight/2 + 8));
            float endY = (float) (centerY + Math.sin(angle*Math.PI/180)*(mLineHeight/2 + 8));
            canvas.drawLine(startX,startY,endX,endY,mPaint);
        }
        invalidate();
    }
    private void startLoadingAnim(){
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

    /**
     * 修改精度条风格
     * @param style
     */
    public void changeProgressBarStyle(CustomProgressBarStyle style){
        mFirstColor = style.mFirstColor;
        mSecondColor = style.mSecondColor;
        mLineHeight = style.mLineHeight;
        mPointColor = style.mPointColor;
        mRadius = style.mRadius;
        mPointRadius = style.mPointRadius;
        invalidate();
    }

    static class CustomProgressBarStyle{

        private int mFirstColor;
        private int mSecondColor;
        private int mPointColor;
        private int mLineHeight;
        private int mRadius;
        private int mPointRadius;

        public CustomProgressBarStyle(){
            mFirstColor = DEFAULT_FIRST_COLOR;
            mSecondColor = DEFAULT_SECOND_COLOR;
            mLineHeight = DEFAULT_LINE_HEIGHT;
            mPointColor = DEFAULT_POINT_COLOR;
            mRadius = 5;
            mPointRadius = DEFAULT_POINT_RADIUS;
        }
        public CustomProgressBarStyle setFirstColor(int color){
            this.mFirstColor = color;
            return this;
        }
        public CustomProgressBarStyle setSecondColor(int color){
            this.mSecondColor = color;
            return this;
        }
        public CustomProgressBarStyle setPointColor(int color){
            this.mPointColor = color;
            return this;
        }
        public CustomProgressBarStyle setLineHeight(int height){
            this.mLineHeight = height;
            return this;
        }
        public CustomProgressBarStyle setRadius(int radius){
            this.mRadius = radius;
            return this;
        }
        public CustomProgressBarStyle setPointRadius(int pointRadius){
            this.mPointRadius = pointRadius;
            return this;
        }
    }

}
