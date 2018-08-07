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
    private val drawSpaceTime = 40
    private lateinit var mPaint:Paint
    private var surfaceWidth = 0f
    private var surfaceHeight = 0f
    private var step = 0.0
    private var mainStep = 500000
    //x方向的单位长度，即20像素代表一个单位长度
    private val UNIT_X = 40.0
    //y方向的单位长度，即20像素代表一个单位长度
    private val UNIT_Y = 80.0


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
            step += PI/36
            if(step >= 2*PI){
                step = 0.0
            }
        }
    }
    //数学坐标到像素点的转换
    private fun coordinate2Pix(coordinate: Double, unit: Double): Int {
        return (coordinate * unit).toInt()
    }

    //像素点到数学坐标的转换
    private fun pix2Coordinate(pix: Int, unit: Double): Double {
        return pix / unit
    }

    private fun doDraw(canvas: Canvas) {
        drawXYLine(canvas)
        drawCos(canvas)
    }
    private fun drawXYLine(canvas: Canvas){
        mPaint.color = Color.BLACK
        canvas.drawLine(0f,surfaceHeight/2,surfaceWidth,surfaceHeight/2,mPaint)
        canvas.drawLine(0f+mPaint.strokeWidth/2,0f,0f+mPaint.strokeWidth/2,surfaceHeight,mPaint)
    }
    private fun drawCos(canvas: Canvas){
        mPaint.color = Color.RED
        var lastPX = 0
        var lastPY = Math.cos(pix2Coordinate(lastPX,UNIT_X)-step)*UNIT_Y + surfaceHeight/2
        var cx = 0.0
        var px = 0
        while (px <= surfaceWidth){
            cx += mainStep
            px = coordinate2Pix(cx,UNIT_X)
            val py = Math.cos(cx-step)*UNIT_Y + surfaceHeight/2
            canvas.drawLine(lastPX.toFloat(),lastPY.toFloat(),px.toFloat(),py.toFloat(),mPaint)
            lastPX = px
            lastPY = py
            px = coordinate2Pix(cx,UNIT_X)
        }
    }
}