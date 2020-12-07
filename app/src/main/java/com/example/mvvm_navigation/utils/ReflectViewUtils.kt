package com.example.mvvm_navigation.utils

import android.content.Context
import android.graphics.*
import android.view.WindowManager

object ReflectViewUtils {

    private const val displayBannerHeight = 880 //從banner 圖片得知 Size = 1440x880
    private const val reflectHeight = 440

    fun reflectionBitmap(ImgBitmap: Bitmap): Bitmap? { //原始圖與倒影間距
        val mGap = 0
        val mWidth = ImgBitmap.width
        val mHeight = ImgBitmap.height
        //建立倒影的Bitmap
        val mMatrix = Matrix()
        mMatrix.preScale(1f, -1f)
        var mReflectionImage: Bitmap? = null
        try {
            mReflectionImage = Bitmap.createBitmap(ImgBitmap, 0, 0, mWidth, mHeight, mMatrix, false)
            mReflectionImage = Bitmap.createBitmap(mReflectionImage, 0, 0, mWidth, mHeight / 2, null, false)
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
        }
        if (mReflectionImage == null) mReflectionImage = ImgBitmap
        var mBitmap: Bitmap? = null
        //原始圖與倒影一起的Bitmap
        try {
            mBitmap = Bitmap.createBitmap(mWidth, mHeight + mHeight, Bitmap.Config.ARGB_8888)
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
        }
        //生成一個空的畫布放 原圖和倒影圖
        if (mBitmap != null) {
            val mCanvas = Canvas(mBitmap)
            //繪製原始圖
            mCanvas.drawBitmap(ImgBitmap, 0f, 0f, null)
            //繪製倒影圖
            val mPaint = Paint()
            mCanvas.drawRect(0f, mHeight.toFloat(), mWidth.toFloat(), mHeight + mGap.toFloat(), mPaint)
            mCanvas.drawBitmap(mReflectionImage, 0f, mHeight + mGap.toFloat(), null)
            //加入透明度
            val mLinearPaint = Paint()
            val mLinearGradient = LinearGradient(0f, ImgBitmap.height.toFloat(), 0f, mBitmap.height - mHeight.toFloat() / 2 + mGap, intArrayOf(-0x1, 0x5cffffff, 0x00000000), floatArrayOf(0.0f, 0.35f, 0.7f), Shader.TileMode.CLAMP)
            mLinearPaint.shader = mLinearGradient
            mLinearPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
            mCanvas.drawRect(0f, mHeight.toFloat(), mWidth.toFloat(), mBitmap.height - mHeight.toFloat() / 2 + mGap, mLinearPaint)
        }
        //高度太高的Banner進行裁切高度的動作
        return cutBitmap(mBitmap)
    }

    fun getDisplayWidth(context: Context?): Int {
        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.x
    }

    private val totalDisplayHeight: Int
        get() = displayBannerHeight + reflectHeight

    private fun cutBitmap(bm: Bitmap?): Bitmap? {
        var bitmap: Bitmap? = null
        if (bm != null) {
            bitmap = Bitmap.createBitmap(bm, 0, 0, bm.width, totalDisplayHeight) //对图片的高度的一半进行裁剪
        }
        return bitmap
    }
}
