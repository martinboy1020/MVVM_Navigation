package com.example.mvvm_navigation.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.mvvm_navigation.R
import java.security.MessageDigest

class ImageShapeWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var attrs: AttributeSet? = null
    private var view: View? = null
    private var binding: ViewDataBinding? = null
    var shapeType: Int? = null
    var roundCornersRadius: Int = 1

    enum class ShapeType(val code: Int) {
        RECTANGLE(0), CIRCLE(1), ROUNDED_CORNERS(2)
    }

    init {
        this.attrs = attrs
        initView()
    }

    private fun initView() {
        view = (context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(R.layout.layout_img_shape_widget, this, true)
        val typedArray =
            this.context.obtainStyledAttributes(this.attrs, R.styleable.ImageShapeWidget)
        shapeType = typedArray.getInt(R.styleable.ImageShapeWidget_shapeType, 0)
        roundCornersRadius = typedArray.getInt(R.styleable.ImageShapeWidget_roundCornersRadius, 1)
        typedArray.recycle()
    }

    fun setImage(imgSrc: String) {
        val img = view?.findViewById<ImageView>(R.id.img_shape_widget)
        if (img != null) {
            when (shapeType) {
                ShapeType.CIRCLE.code -> Glide.with(this).load(imgSrc).circleCrop()
                    .into(img)
                ShapeType.ROUNDED_CORNERS.code -> Glide.with(this).load(imgSrc)
                    .transform(CenterCrop(), RoundedCorners(roundCornersRadius)).into(img)
                else -> Glide.with(this).load(imgSrc).into(img)
            }
        }
    }

    class CircleTransform : BitmapTransformation() {

        override fun updateDiskCacheKey(messageDigest: MessageDigest) {

        }

        override fun transform(
            pool: BitmapPool,
            toTransform: Bitmap,
            outWidth: Int,
            outHeight: Int
        ): Bitmap {
            return circleCrop(pool, toTransform)!!
        }

        val id: String
            get() = javaClass.name

        private fun circleCrop(pool: BitmapPool, source: Bitmap?): Bitmap? {
            if (source == null) return null
            val size = Math.min(source.width, source.height)
            val x = (source.width - size) / 2
            val y = (source.height - size) / 2
            val squared = Bitmap.createBitmap(source, x, y, size, size)
            var result: Bitmap? = pool[size, size, Bitmap.Config.ARGB_8888]
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
            }
            val canvas = Canvas(result!!)
            val paint = Paint()
            paint.shader = BitmapShader(
                squared,
                Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP
            )
            paint.isAntiAlias = true
            val r = size / 2f
            canvas.drawCircle(r, r, r, paint)
            return result
        }
    }

}