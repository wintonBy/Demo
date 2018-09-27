package com.winton.demo.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.*
import java.io.FileInputStream
import java.io.InputStream

/**
 * @author: winton
 * @time: 2018/9/27 上午11:53
 * @desc: 描述
 */
class BigImageView:SurfaceView ,GestureDetector.OnGestureListener,SurfaceHolder.Callback,Runnable{

    private lateinit var surfaceHolder: SurfaceHolder

    /**
     * 大图解码器
     */
    private lateinit var decoder:BitmapRegionDecoder
    /**
     * 图片解码配置
     */
    private lateinit var option:BitmapFactory.Options
    /**
     * 绘制的区域
     */
    private var rect = Rect()

    private var lastRect = Rect()
    /**
     * 上次滑动区域
     */
    private var lastX = 0f
    private var lastY = 0f
    /**
     * 图片长宽
     */
    private var imageWidth = 0
    private var imageHeight = 0
    /**
     * 手势控制器
     */
    private lateinit var gestureDetector:GestureDetector
    private var scaleTouchSlop = 0

    private val filePath = ""
    private val refreshSpace = 40

    private var runflag = false
    private var moveflag = false

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        doInit()
    }



    private fun doInit(){
        surfaceHolder = holder

        surfaceHolder.addCallback(this)

        gestureDetector = GestureDetector(context,this)
        scaleTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
        option = BitmapFactory.Options()
        option.inPreferredConfig = Bitmap.Config.RGB_565

        val fileIS = resources.assets.open("big_img.jpeg")
        val tmpOption = BitmapFactory.Options()
        tmpOption.inJustDecodeBounds = true
        BitmapFactory.decodeStream(fileIS,null,tmpOption)
        imageWidth = tmpOption.outWidth
        imageHeight = tmpOption.outHeight

        decoder = BitmapRegionDecoder.newInstance(fileIS,false)

        fileIS?.close()
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        runflag = false
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        runflag = true
        Thread(this).start()
    }

    override fun run() {
        while (runflag){
            var start = System.currentTimeMillis()
            if(rect.equals(lastRect)){
                continue
            }
            val canvas = surfaceHolder.lockCanvas()
            var bitmap = decoder.decodeRegion(rect,option)
            canvas.drawBitmap(bitmap,0f,0f,null)
            lastRect.set(rect)
            surfaceHolder.unlockCanvasAndPost(canvas)
            bitmap.recycle()
            var end = System.currentTimeMillis()
            while(end - start < refreshSpace){
                Thread.yield()
                end = System.currentTimeMillis()
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        event?.let {
//            return gestureDetector.onTouchEvent(it)
//        }

        when(event?.action){
            MotionEvent.ACTION_DOWN ->{
                lastX = event.rawX
                lastY = event.rawY
            }
            MotionEvent.ACTION_MOVE ->{
                move(event.rawX,event.rawY)
            }
            MotionEvent.ACTION_UP ->{

            }
        }
        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width = measuredWidth
        var height = measuredHeight

        rect.left = imageWidth/2 - width/2
        rect.top = imageHeight/2 - height/2
        rect.right = rect.left + width
        rect.bottom = rect.top + height
    }


    override fun onShowPress(e: MotionEvent?) {
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onDown(e: MotionEvent?): Boolean {
        e?.let {
            lastX = it.rawX
            lastY = it.rawY
        }
        return  true
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        e2?.let {
            move(e2.rawX,e2.rawY)
        }
        return true
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        e2?.let {
            move(e2.rawX,e2.rawY)
        }
        return  true
    }

    override fun onLongPress(e: MotionEvent?) {
        e?.let {
            lastX = it.rawX
            lastY = it.rawY
        }
    }


    private fun  move(x:Float,y:Float){

        var dx = x - lastX
        var dy = y - lastY

        if(imageWidth > width){
            rect.offset(-dx.toInt(),0)

            if(rect.left < 0){
                rect.left = 0
                rect.right = width
            }

            if(rect.right >imageWidth){
                rect.right = imageWidth
                rect.left= imageWidth - width
            }
        }

        if(imageHeight > height){
            rect.offset(0,-dy.toInt())
            if(rect.top <0){
                rect.top  = 0
                rect.bottom = height
            }

            if(rect.bottom > imageHeight){
                rect.bottom = imageHeight
                rect.top = imageHeight - height
            }
        }
        lastX = x
        lastY = y
    }
}