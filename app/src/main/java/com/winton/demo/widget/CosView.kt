package com.winton.demo.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlin.math.PI

/**
 * @author: winton
 * @time: 2018/8/5 22:18
 * @package: com.winton.demo.widget
 * @project: Demo
 * @mail:
 * @describe: 一句话描述
 */
class CosView :SurfaceView,SurfaceHolder.Callback,Runnable {

    private var isRunning = false
    private lateinit var surfaceHolder:SurfaceHolder
    private val drawSpaceTime = 30
    private lateinit var mPaint:Paint
    private var surfaceWidth = 0f
    private var surfaceHeight = 0f
    private var step = 0.0


    constructor(context: Context?) : super(context){
        initData()
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        initData()
    }

    private fun initData(){
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.color = Color.RED
        mPaint.strokeWidth = 2f
        mPaint.style = Paint.Style.STROKE
        surfaceHolder = holder
        surfaceHolder.addCallback(this)
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        surfaceWidth = width + 0f
        surfaceHeight = height + 0f
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        isRunning = false
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        isRunning = true
        Thread(this).start()
    }

    override fun run() {
        while (isRunning){
            var startTime = System.currentTimeMillis()
            var canvas = surfaceHolder.lockCanvas()
            canvas.drawColor(Color.WHITE)
            doDraw(canvas)
            surfaceHolder.unlockCanvasAndPost(canvas)
            var endTime = System.currentTimeMillis()
            var space = endTime - startTime
            while (space <= drawSpaceTime){
                Thread.yield()
                space = System.currentTimeMillis() - startTime
            }
            step += PI/20
            if(step >= 2*PI){
                step = 0.0
            }
        }
    }

    private fun doDraw(canvas: Canvas) {
        drawXYLine(canvas)
        drawSin(canvas)
    }
    private fun drawXYLine(canvas: Canvas){
        mPaint.color = Color.BLACK
        canvas.drawLine(0f,surfaceHeight/2,surfaceWidth,surfaceHeight/2,mPaint)
        canvas.drawLine(0f+mPaint.strokeWidth/2,0f,0f+mPaint.strokeWidth/2,surfaceHeight,mPaint)
    }
    private fun drawSin(canvas: Canvas){
        mPaint.color = Color.RED
        mPaint.strokeWidth = 2f
        var lastX = mPaint.strokeWidth/2
        var lastY = surfaceHeight/2
        val t = 40
        var x = 0.0
        while (x <= surfaceWidth){
            val y = Math.cos(x/t -step) * 80 + surfaceHeight/2
            canvas.drawLine(lastX,lastY,x.toFloat(),y.toFloat(),mPaint)
            lastX = x.toFloat()
            lastY = y.toFloat()
            x  += 0.1
        }
    }
}