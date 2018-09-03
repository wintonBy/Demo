package com.winton.demo.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

/**
 * @author: winton
 * @time: 2018/8/7 10:57
 * @package: com.winton.demo.widget
 * @project: Demo
 * @mail:
 * @describe: 画板
 */
class PicturePaper:SurfaceView,SurfaceHolder.Callback,Runnable {

    private lateinit var mPaint:Paint
    private lateinit var surfaceHolder: SurfaceHolder
    private var strokeWidth = 10f
    private var mColor = Color.RED
    private var mBgColor = Color.WHITE
    @Volatile private var runningFlag: Boolean = false
    @Volatile private var drawing: Boolean = false
    private val refreshSpace = 40
    private var pathList = ArrayList<Path>()
    var lastX = 0f
    var lastY = 0f
    private var path:Path? = null

    private var drawCount = 0;

    constructor(context: Context?) : super(context){
        initData()
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        initData()
    }

    private fun initData(){
        isFocusable = true
        mPaint =  Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.strokeWidth = strokeWidth
        mPaint.color = mColor
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.style = Paint.Style.STROKE
        surfaceHolder = holder
        surfaceHolder.addCallback(this)
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        runningFlag = false
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        runningFlag = true
        Thread(this).start()
    }


    override fun run() {
        var canvas = surfaceHolder.lockCanvas()
        canvas.drawColor(mBgColor)
        drawPath(canvas)
        surfaceHolder.unlockCanvasAndPost(canvas)
        while (runningFlag){
            var start = System.currentTimeMillis()
            while (drawing){
                val canvas = surfaceHolder.lockCanvas()
                canvas.drawColor(mBgColor)
                drawPath(canvas)
                surfaceHolder.unlockCanvasAndPost(canvas)
            }
            var end = System.currentTimeMillis()
            while(end - start < refreshSpace){
                Thread.yield()
                end = System.currentTimeMillis()
            }
        }
    }

    /**
     * 绘制笔画
     */
    private fun drawPath(canvas: Canvas) {
        for(path in pathList){
            canvas.drawPath(path,mPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var action = event!!.action

        when(action){
            MotionEvent.ACTION_DOWN -> {
                drawing = true
                path = Path()
                pathList.add(path!!)
                drawDown(path!!,event)
                lastX = event.x
                lastY = event.y
            }
            MotionEvent.ACTION_MOVE ->{
                drawMove(path!!,event)
            }
            MotionEvent.ACTION_UP -> {
                drawing = false
            }
        }
        return true
    }

    /**
     * 绘制手指滑动时
     */
    private fun drawMove(path: Path, event: MotionEvent) {
        var x = event.x
        var y = event.y
        if(Math.abs(x-lastX)>3 || Math.abs(y-lastY)>3){
            path.quadTo(lastX,lastY,(x+lastX)/2,(y+lastY)/2)
            lastX = event.x
            lastY = event.y
        }
    }

    /**
     * 绘制手指按下时
     */
    private fun drawDown(path: Path, event: MotionEvent) {
        var x = event.x
        var y = event.y
        path.moveTo(x,y)
    }
}