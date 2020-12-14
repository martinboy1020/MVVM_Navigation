package com.example.mvvm_navigation.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.mvvm_navigation.R

class BuildRecyclerView constructor(context: Context, val attributeSet: AttributeSet? = null) :
    RecyclerView(context, attributeSet) {

    init {
        init()
    }

    enum class ManagerType(val code: Int) {
        HORIZONTAL(0), VERTICAL(1), GRID(2)
    }

    private fun init() {
        val typedArray =
            this.context.obtainStyledAttributes(this.attributeSet, R.styleable.BuildRecyclerView)
        val managerType = typedArray.getInt(R.styleable.BuildRecyclerView_layout_manager_type, 0)
        val column = typedArray.getInt(R.styleable.BuildRecyclerView_column, 0)
        val hasDivider = typedArray.getBoolean(R.styleable.BuildRecyclerView_has_divider, false)
        val dividerHeight = typedArray.getInt(R.styleable.BuildRecyclerView_divider_height, 1)
        val itemSpaceMarginTop = typedArray.getInt(R.styleable.BuildRecyclerView_item_space_margin, 0)
        this.layoutManager =
            when (managerType) {
                ManagerType.GRID.code -> GridLayoutManager(this.context, column)
                else -> LinearLayoutManager(this.context, managerType, false)
            }
        if (hasDivider)
            this.addItemDecoration(DividerItemDecoration(this.context, dividerHeight, itemSpaceMarginTop))
        if(managerType == ManagerType.GRID.code) {
            this.addItemDecoration(GridSpacingItemDecoration(2, 20, false))
        }
        typedArray.recycle()
    }
}

class DividerItemDecoration(context: Context, dividerHeight: Int, itemSpaceMarginTop: Int) : RecyclerView.ItemDecoration() {

    private var mContext = context
    private var mDividerHeight = 0f
    private var mItemSpaceMarginTop = 0f
    private var mPaint: Paint? = null

    init {
        mPaint = Paint()
        mPaint?.isAntiAlias = true
        mPaint?.color = Color.BLACK
        mDividerHeight = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dividerHeight.toFloat(),
            mContext.resources.displayMetrics
        )
        mItemSpaceMarginTop = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            itemSpaceMarginTop.toFloat(),
            mContext.resources.displayMetrics
        )
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) != 0) {
            // 第二個ItemView開始與上方的間距
            outRect.top = mItemSpaceMarginTop.toInt()
        }
        val position = parent.getChildAdapterPosition(view)

        // 更換背景顏色
        view.setBackgroundColor(if (position % 2 == 0) ContextCompat.getColor(mContext, R.color.green) else ContextCompat.getColor(mContext, R.color.yellow))
    }

    // 繪製於背景之下 內容會在上方
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {

    }

    // 繪製於內容之上 內容會被覆蓋
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val view = parent.getChildAt(i)
            val index = parent.getChildAdapterPosition(view)
            //第一個ItemView不需要繪製
            if (index == 0) {
                continue
            }
            val dividerTop = view.top - mDividerHeight
            val dividerLeft = parent.paddingLeft.toFloat()
            val dividerBottom = view.top.toFloat()
            val dividerRight = (parent.width - parent.paddingRight).toFloat()
            c.drawRect(dividerLeft, dividerTop, dividerRight, dividerBottom, mPaint!!)
        }
    }
}

class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacing: Int,
    private val includeEdge: Boolean = false
) :
    ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column
        if (includeEdge) {
            outRect.left =
                spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
            outRect.right =
                (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
            if (position < spanCount) { // top edge
                outRect.top = spacing
            }
            outRect.bottom = spacing // item bottom
        } else {
            outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
            outRect.right =
                spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing // item top
            }
        }
    }

}