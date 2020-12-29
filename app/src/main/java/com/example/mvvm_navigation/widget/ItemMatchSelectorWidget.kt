package com.example.mvvm_navigation.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.mvvm_navigation.R

@BindingAdapter("imageUrl", "itemTitle", "listener", requireAll = false)
fun setListener(
    view: ItemMatchSelectorWidget,
    imageUrl: String,
    itemTitle: String,
    listener: ItemMatchSelectorWidget.CheckBoxListener
) {
    view.setListener(imageUrl, itemTitle, listener)
}

class ItemMatchSelectorWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var view: View? = null
    private var attrs: AttributeSet? = null
    private var type: Int? = -1

    enum class FilterType(val type: Int) {
        LEAGUE(0), HOME(1), AWAY(2)
    }


    interface CheckBoxListener {
        fun getFilterType(type: Int, isChecked: Boolean)
    }

    init {
        this.attrs = attrs
        initView()
    }

    private fun initView() {
        view = View.inflate(context, R.layout.layout_item_cb_match_selector, this)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemMatchSelectorWidget)
        type = typedArray.getInt(R.styleable.ItemMatchSelectorWidget_itemType, -1)
        val checkBox = view?.findViewById<CheckBox>(R.id.cb_item_match_selector)
        if (type == FilterType.LEAGUE.type) checkBox?.isChecked = true
        typedArray.recycle()
    }

    fun setListener(imgUrl: String, itemTitle: String, listener: CheckBoxListener) {
        val imageShapeWidget = view?.findViewById<ImageShapeWidget>(R.id.img_item_match_selector)
        imageShapeWidget?.setImage(imgUrl)
        val tvTitle = view?.findViewById<TextView>(R.id.tv_item_match_selector)
        tvTitle?.text = itemTitle
        val checkBox = view?.findViewById<CheckBox>(R.id.cb_item_match_selector)
        checkBox?.setOnCheckedChangeListener { _, b ->
            type?.let { listener.getFilterType(it, b) }
        }
    }

}