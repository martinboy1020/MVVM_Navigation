package com.example.mvvm_navigation.widget

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSeekBar
import com.example.mvvm_navigation.R

class TextThumbSeekBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.seekBarStyle)
    : AppCompatSeekBar(context, attrs, defStyleAttr) {

    var lastSeekBarValue: Int = 0
    private val mThumbSize: Int = 120
    private val mTextPaint: TextPaint = TextPaint()

    @Synchronized
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val progressText = progress.toString()
        val bounds = Rect()
        mTextPaint.getTextBounds(progressText, 0, progressText.length, bounds)
        val leftPadding = paddingLeft - thumbOffset
        val rightPadding = paddingRight - thumbOffset
        val width = width - leftPadding - rightPadding
        val progressRatio = progress.toFloat() / max
        val thumbOffset = mThumbSize * (.5f - progressRatio)
        val thumbX = progressRatio * width + leftPadding + thumbOffset
        val thumbY: Float = height / 2f + bounds.height() / 2f
        canvas.drawText(progressText, thumbX, thumbY, mTextPaint)
    }

    init {
//        mThumbSize = resources.getDimensionPixelSize(R.dimen.thumb_size)
        mTextPaint.color = Color.WHITE
//        mTextPaint.textSize = resources.getDimensionPixelSize(R.dimen.thumb_text_size).toFloat()
        mTextPaint.textSize = 32f
        mTextPaint.typeface = Typeface.DEFAULT_BOLD
        mTextPaint.textAlign = Paint.Align.CENTER
    }
}