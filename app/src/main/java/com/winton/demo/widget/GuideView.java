package com.winton.demo.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;

import com.winton.demo.R;

import java.util.ArrayList;

/**
 * @author: winton
 * @time: 2018/1/12 10:19
 * @package: com.winton.demo.widget
 * @project: Demo
 * @mail:
 * @describe: 功能介绍View
 */
public class GuideView extends View {
    //高亮类型：矩形、圆形、椭圆形
    public static final int VIEWSTYLE_RECT = 0;
    public static final int VIEWSTYLE_CIRCLE = 1;
    public static final int VIEWSTYLE_OVAL = 2;

    //画笔类型，圆滑、默认
    public static final int MASKBLURSTYLE_SOLID = 0;
    public static final int MASKBLURSTYLE_NORMAL = 1;

    private View rootView;//activity的contentview,是FrameLayout
    private Bitmap jtUpLeft, jtUpRight, jtDownRight, jtDownLeft;// 指示箭头
    private Bitmap fgBitmap;// 前景
    private Canvas mCanvas;// 绘制蒙版层的画布
    private Paint mPaint;// 绘制蒙版层画笔
    private int screenW, screenH;// 屏幕宽高
    private static final int margin = 40;
    private int radius;//圆半径
    private OnDismissListener onDismissListener;//关闭监听
    private Activity activity;

    /*******************可配置属性*****************************/
    private boolean touchOutsideCancel = true;//外部点击是否可关闭
    private int highLightStyle = VIEWSTYLE_RECT;//高亮类型默认圆形
    public int maskblurstyle = MASKBLURSTYLE_SOLID;//画笔类型默认
    private ArrayList<Bitmap> tipBitmaps;//显示图片
    private ArrayList<View> targetViews;//高亮目标view
    private int maskColor = 0x99000000;// 蒙版层颜色
    private int borderWitdh = 10;
    private int highLisghtPadding = 0;// 高亮控件padding

    private GuideView(Activity activity) {
        super(activity);
        this.activity=activity;
    }

    private void cal(Activity activity){
        int[] screenSize = getScreenSize(activity);
        screenW = screenSize[0];
        screenH = screenSize[1];
    }

    private void init(Activity activity){
        rootView = activity.findViewById(android.R.id.content);
        // 实例化画笔并开启其抗锯齿和抗抖动
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        // 设置画笔透明度为0是关键！
        mPaint.setARGB(0, 255, 0, 0);
        // 设置混合模式为DST_IN
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        BlurMaskFilter.Blur blurStyle = null;
        switch (maskblurstyle) {
            case MASKBLURSTYLE_SOLID:
                blurStyle = BlurMaskFilter.Blur.SOLID;
                break;
            case MASKBLURSTYLE_NORMAL:
                blurStyle = BlurMaskFilter.Blur.NORMAL;
                break;
        }
        mPaint.setMaskFilter(new BlurMaskFilter(15, blurStyle));
        // 生成前景图Bitmap
        fgBitmap = Bitmap.createBitmap(screenW, screenH, Bitmap.Config.ARGB_4444);
        //创建一个bitmap大小的画布
        mCanvas = new Canvas(fgBitmap);
        //绘制前景画布颜色
        mCanvas.drawColor(maskColor);
        //实例化箭头
        jtDownLeft = BitmapFactory.decodeResource(getResources(), R.drawable.hl_down_left);
        jtDownRight = BitmapFactory.decodeResource(getResources(), R.drawable.hl_down_right);
        jtUpLeft = BitmapFactory.decodeResource(getResources(), R.drawable.hl_up_left);
        jtUpRight = BitmapFactory.decodeResource(getResources(), R.drawable.hl_up_right);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(targetViews  == null && tipBitmaps == null){
            return;
        }
        //绘制前景
        canvas.drawBitmap(fgBitmap,0,0,null);
        //绘制高亮控件
        if(targetViews.size() >0 && tipBitmaps.size() >0){
            for(int i=0;i< targetViews.size();i++){
                //高亮控件的长宽
                int vWidth = targetViews.get(i).getWidth();
                int vHeight = targetViews.get(i).getHeight();
                //获取该控件的坐标
                int left=0;
                int top = 0;
                int right = 0;
                int bottom = 0;
                try{
                    Rect rtLocation = getLocationInView(((ViewGroup)activity.findViewById(android.R.id.content)).getChildAt(0),targetViews.get(i));
                    left = rtLocation.left;
                    top = rtLocation.top;
                    right = rtLocation.right;
                    bottom = rtLocation.bottom;
                }catch (Exception e){
                    e.printStackTrace();
                }
                //绘制高亮形状
                switch (highLightStyle){
                    case VIEWSTYLE_RECT:
                        RectF rectf = new RectF(left-borderWitdh-highLisghtPadding,top-borderWitdh-highLisghtPadding,right+borderWitdh+highLisghtPadding,bottom+borderWitdh+highLisghtPadding);
                        mCanvas.drawRoundRect(rectf,20,20,mPaint);
                        break;
                    case VIEWSTYLE_OVAL:
                        RectF ovalRect = new RectF(left-highLisghtPadding,top-highLisghtPadding,right+highLisghtPadding,bottom+highLisghtPadding);
                        mCanvas.drawOval(ovalRect,mPaint);
                        break;
                    case VIEWSTYLE_CIRCLE:
                    default:
                        radius = vWidth > vHeight? vWidth/2 + highLisghtPadding/2:vHeight/2 + highLisghtPadding/2;
                        if(radius < 50){
                            radius = 100;
                        }
                        mCanvas.drawCircle(left + vWidth/2 ,top + vHeight/2,radius,mPaint);
                        break;
                }
                if(bottom < screenH/2 || screenH/2 - top > bottom - screenH/2){
                    //TargetView 偏上
                    int jtTop = highLightStyle == VIEWSTYLE_CIRCLE ?
                            bottom +highLisghtPadding + margin+radius/3 : bottom +highLisghtPadding+ margin;
                    if(right < screenW / 2 || (screenW / 2 - left > right - screenW / 2)){
                       //偏左
                        canvas.drawBitmap(jtUpLeft, left + vWidth / 2, jtTop, null);//箭头
                        if (tipBitmaps.get(i) != null) {
                            //提示文字
                            canvas.drawBitmap(tipBitmaps.get(i), left + vWidth / 2, jtTop + jtUpLeft.getHeight(), null);
                        }
                    }else {
                        //偏右
                        canvas.drawBitmap(jtUpRight,left + vWidth/2 -100 - margin,jtTop,null);
                        if(tipBitmaps.get(i) != null){
                            //提示文字
                            canvas.drawBitmap(tipBitmaps.get(i),left+vWidth/2 -100 -tipBitmaps.get(i).getWidth()/2,jtTop+jtUpRight.getHeight(),null);
                        }
                    }
                }else {
                    //TargetView 偏下
                    int jtTop = highLightStyle == VIEWSTYLE_CIRCLE ?
                            top -highLisghtPadding - margin-radius/3 -jtDownLeft.getHeight(): top -highLisghtPadding - margin - jtDownLeft.getHeight();
                    if(right < screenW / 2 || (screenW / 2 - left > right - screenW / 2)){
                        //偏左
                        canvas.drawBitmap(jtDownLeft,left+vWidth/2,jtTop,null);
                        if(tipBitmaps.get(i)!= null){
                            canvas.drawBitmap(tipBitmaps.get(i),left+vWidth/2,jtTop - tipBitmaps.get(i).getHeight(),null);
                        }
                    }else {
                        //偏右
                        canvas.drawBitmap(jtDownRight,left+vWidth/2-100-margin,jtTop,null);
                        if(tipBitmaps.get(i) != null){
                            canvas.drawBitmap(tipBitmaps.get(i),left+vWidth/2 -100 -tipBitmaps.get(i).getWidth()/2-margin,jtTop-tipBitmaps.get(i).getHeight(),null);
                        }
                    }
                }
            }
        }else if(tipBitmaps.size() >0){
            //无高亮控件
            canvas.drawBitmap(tipBitmaps.get(0),screenW-tipBitmaps.get(0).getWidth()/2,screenH-tipBitmaps.get(0).getHeight()/2,null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                if(touchOutsideCancel){
                    this.setVisibility(GONE);
                    if(rootView !=  null){
                        ((ViewGroup)rootView).removeView(this);
                    }
                    if(onDismissListener != null){
                        onDismissListener.onDismiss();
                    }
                    return true;
                }
                break;
        }
        return true;
    }

    /**
     * 获取屏幕尺寸
     *
     * @param activity
     *            Activity
     * @return 屏幕尺寸像素值，下标为0的值为宽，下标为1的值为高
     */
    private int[] getScreenSize(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return new int[] { metrics.widthPixels, metrics.heightPixels };
    }

    /**
     * 获取View在父控件上的坐标
     * @param parent
     * @param child
     * @return
     */
    private Rect getLocationInView(View parent,View child){
        if(parent == null || child == null){
            throw new IllegalArgumentException("parent or child should not be null");
        }
        View decorView = null;

        if(activity != null){
            decorView = activity.getWindow().getDecorView();
        }
        Rect result = new Rect();
        Rect tmpRect = new Rect();

        View tmp = child;

        if(child == parent){
            child.getHitRect(result);
            return result;
        }
        while (tmp != decorView && tmp != parent){
            tmp.getHitRect(tmpRect);
            if(!tmp.getClass().equals(FRAGMENT_CON)){
                result.left += tmpRect.left;
                result.top += tmpRect.top;
            }
            tmp = (View) tmp.getParent();
        }
        result.right = result.left+child.getMeasuredWidth();
        result.bottom = result.top + child.getMeasuredHeight();
        return result;
    }
    private static final String FRAGMENT_CON = "NoSaveStateFrameLayout";

    /**
     * 让View展示出来
     */
    public void show(){
        if(rootView != null){
            invalidate();
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup)rootView).addView(this,((ViewGroup)rootView).getChildCount(),lp);
        }
    }

    public interface OnDismissListener {
        public void onDismiss();
    }

    /**************************对外的建造者模式*******************************/

    public static class Builder{
        private GuideView guideView;

        public Builder(Activity activity){
            guideView = new GuideView(activity);
        }
        public Builder setMaskView(int maskColor){
            guideView.maskColor = maskColor;
            return this;
        }
        public Builder setHighLightStyle(int style){
            guideView.highLightStyle = style;
            return this;
        }
        public Builder setMaskblurstyle(int maskblurstyle){
            guideView.maskblurstyle = maskblurstyle;
            return this;
        }
        public Builder addHighLightGuideView(View targetView,@DrawableRes int res){
            if(guideView.targetViews == null){
                guideView.targetViews = new ArrayList<>();
            }
            if(guideView.tipBitmaps == null){
                guideView.tipBitmaps = new ArrayList<>();
            }
            try{
                guideView.targetViews.add(targetView);
                guideView.tipBitmaps.add(BitmapFactory.decodeResource(guideView.activity.getResources(),res));
            }catch (Exception e){
                e.printStackTrace();
            }
            return this;
        }
        public Builder addNoHighLightGuideView(@DrawableRes int res){
            if(guideView.tipBitmaps == null){
                guideView.tipBitmaps = new ArrayList<>();
            }
            try{
                guideView.tipBitmaps.add(BitmapFactory.decodeResource(guideView.activity.getResources(),res));
            }catch (Exception e){
                e.printStackTrace();
            }
            return this;
        }
        public Builder setTouchOutsideDismiss(boolean cancel){
            guideView.touchOutsideCancel = cancel;
            return this;
        }
        public Builder setBorderWidth(int width){
            guideView.borderWitdh = width;
            return this;
        }
        public Builder setDismissListener(OnDismissListener listener){
            guideView.onDismissListener = listener;
            return this;
        }

        public GuideView build(){
            guideView.cal(guideView.activity);
            guideView.init(guideView.activity);
            return guideView;
        }
    }
}
