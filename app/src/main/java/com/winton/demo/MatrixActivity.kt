package com.winton.demo

import android.graphics.*
import android.os.Bundle
import android.widget.ImageView
import android.graphics.Bitmap
import android.widget.SeekBar
import java.util.*


/**
 * @author: winton
 * @time: 2018/9/25 下午8:09
 * @desc: 描述
 */
class MatrixActivity :BaseActivity(){


    var matrix = FloatArray(20)

    var mroate = 1f
    var mscale = 1f
    var msaturation = 1f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_matrix)
        translateImage()
        roateImage()
        whiteBlockFilter()
        revserColorFilter()
        var bitmap = BitmapFactory.decodeResource(resources,R.mipmap.image)
        findViewById<SeekBar>(R.id.seek_roate).setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mroate = progress.toFloat()
                var bmp = effectFilter(bitmap,mroate,msaturation,mscale)
                findViewById<ImageView>(R.id.img5).setImageBitmap(bmp)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        findViewById<SeekBar>(R.id.seek_satuation).setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                msaturation  = progress.toFloat()/10f
                var bmp = effectFilter(bitmap,mroate,msaturation,mscale)
                findViewById<ImageView>(R.id.img5).setImageBitmap(bmp)

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        findViewById<SeekBar>(R.id.seek_scale).setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mscale = progress.toFloat()/10f
                var bmp = effectFilter(bitmap,mroate,msaturation,mscale)
                findViewById<ImageView>(R.id.img5).setImageBitmap(bmp)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    /**
     * 对图片镜像
     */
    private fun translateImage(){
        var bitmap = BitmapFactory.decodeResource(resources,R.mipmap.image)
        var matrix = Matrix()
        matrix.setScale(-1f,1f)
        matrix.postTranslate(bitmap.width.toFloat(),0f)
        var mbit = Bitmap.createBitmap(bitmap,0,0,bitmap.width,bitmap.height,matrix,true)
        findViewById<ImageView>(R.id.img1).setImageBitmap(mbit)
    }

    /**
     * 旋转图片45度
     */
    private fun roateImage(){
        var bitmap = BitmapFactory.decodeResource(resources,R.mipmap.image)
        var matrix = Matrix()
        matrix.setRotate(45f,bitmap.width/2f,bitmap.height/2f)
        var mbit = Bitmap.createBitmap(bitmap,0,0,bitmap.width,bitmap.height,matrix,true)
        findViewById<ImageView>(R.id.img2).setImageBitmap(mbit)
    }

    /**
     * 色彩滤镜 黑白滤镜
     */
    private fun whiteBlockFilter(){
        var bitmap = BitmapFactory.decodeResource(resources,R.mipmap.image)
        Arrays.fill(matrix,0f)
        matrix[0] =1f
        matrix[6] =1f
        matrix[12] =1f
        matrix[18] = 1f
        matrix[0] = 0.33f
        matrix[1] = 0.59f
        matrix[2] = 0.11f
        matrix[5] = 0.33f
        matrix[6] = 0.59f
        matrix[7] = 0.11f
        matrix[10] = 0.33f
        matrix[11] = 0.59f
        matrix[12] = 0.11f
        val bmp = colorFilter(bitmap,matrix)
        findViewById<ImageView>(R.id.img3).setImageBitmap(bmp)
    }

    /**
     * 胶片效果
     */
    private fun revserColorFilter(){
        var bitmap = BitmapFactory.decodeResource(resources,R.mipmap.image)
        Arrays.fill(matrix,0f)
        matrix[0] =1f
        matrix[6] =1f
        matrix[12] =1f
        matrix[18] = 1f

        matrix[0]= -1f
        matrix[3] = 1f
        matrix[4] = 1f
        matrix[6] = -1f
        matrix[8] = 1f
        matrix[9] = 1f
        matrix[12] = -1f
        matrix[13] = 1f
        matrix[14] =1f
        matrix[18] = 1f
        val bmp = colorFilter(bitmap,matrix)
        findViewById<ImageView>(R.id.img4).setImageBitmap(bmp)
    }





    /**
     * 直接传入滤镜
     */
    private fun colorFilter(bitmap: Bitmap, colorMatrix: FloatArray):Bitmap{
        val bmp = Bitmap.createBitmap(bitmap.width,bitmap.height,bitmap.config)
        var paint = Paint()
        paint.colorFilter = ColorMatrixColorFilter(ColorMatrix(colorMatrix))
        var canvas = Canvas(bmp)
        canvas.drawBitmap(bitmap,0f,0f,paint)
        return bmp
    }

    /**
     * 调色
     */
    private fun effectFilter(bitmap: Bitmap,roate:Float,saturation:Float,scale:Float):Bitmap{
        val bmp = Bitmap.createBitmap(bitmap.width,bitmap.height,bitmap.config)
        var paint = Paint()
        var canvas = Canvas(bmp)

        /**
         * 修改色调，0 red 1 green 2 blue
         */
        var roateMatrix = ColorMatrix()
        roateMatrix.setRotate(0,roate)
        roateMatrix.setRotate(1,roate)
        roateMatrix.setRotate(2,roate)

        /**
         * 饱和度
         */
        var saturationMatrix = ColorMatrix()
        saturationMatrix.setSaturation(saturation)

        /**
         * 亮度
         */
        var scaleMatrix = ColorMatrix()
        scaleMatrix.setScale(scale,scale,scale,1f)

        var colorMatrix = ColorMatrix()

        colorMatrix.postConcat(roateMatrix)
        colorMatrix.postConcat(saturationMatrix)
        colorMatrix.postConcat(scaleMatrix)

        paint.colorFilter = ColorMatrixColorFilter(colorMatrix)
        canvas.drawBitmap(bitmap,0f,0f,paint)
        return bmp

    }


}