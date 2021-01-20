package com.example.mvvm_navigation.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.base.utils.DisplayUtility
import com.example.mvvm_navigation.R
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager


class BuildRecyclerView constructor(context: Context, val attributeSet: AttributeSet? = null) :
    RecyclerView(context, attributeSet) {

    init {
        init()
    }

    enum class ManagerType(val code: Int) {
        HORIZONTAL(0), VERTICAL(1), GRID(2), FLEXBOX(3)
    }

    private fun init() {
        val typedArray = this.context.obtainStyledAttributes(this.attributeSet, R.styleable.BuildRecyclerView)
        val managerType = typedArray.getInt(R.styleable.BuildRecyclerView_layout_manager_type, 0)
        val column = typedArray.getInt(R.styleable.BuildRecyclerView_column, 0)
        val hasDivider = typedArray.getBoolean(R.styleable.BuildRecyclerView_has_divider, false)
        val dividerHeight = typedArray.getInt(R.styleable.BuildRecyclerView_divider_height, 1)
        val setGridSpace = typedArray.getBoolean(R.styleable.BuildRecyclerView_set_grid_space, false)
        val gridSpacing = typedArray.getInt(R.styleable.BuildRecyclerView_grid_spacing, 1)
        val itemSpaceMarginTop = typedArray.getInt(R.styleable.BuildRecyclerView_item_space_margin, 0)
        this.layoutManager =
            when (managerType) {
                ManagerType.GRID.code -> GridLayoutManager(this.context, column)
                ManagerType.FLEXBOX.code -> FlexboxLayoutManager(this.context)
                else -> LinearLayoutManager(this.context, managerType, false)
            }
        if (managerType == ManagerType.FLEXBOX.code) {
            (this.layoutManager as FlexboxLayoutManager).flexDirection = FlexDirection.ROW
            (this.layoutManager as FlexboxLayoutManager).flexWrap = FlexWrap.WRAP
            (this.layoutManager as FlexboxLayoutManager).alignItems = AlignItems.STRETCH
        }
        if ((managerType == ManagerType.VERTICAL.code || managerType == ManagerType.HORIZONTAL.code) && hasDivider)
            this.addItemDecoration(
                DividerItemDecoration(
                    this.context,
                    (this.layoutManager as LinearLayoutManager).orientation,
                    dividerHeight
                )
            )
        if(managerType == ManagerType.GRID.code && setGridSpace) {
            this.addItemDecoration(GridSpacingItemDecoration((this.layoutManager as GridLayoutManager).spanCount, gridSpacing, false))
        }
        typedArray.recycle()
    }

    class DividerItemDecoration(context: Context, orientation: Int, dividerHeight: Int) :
        ItemDecoration() {
        private var mDivider: Drawable?

        //佈局方向，決定繪製水平分隔線還是豎直分隔線
        private var mOrientation = 0
        private val mBounds = Rect()

        fun setOrientation(orientation: Int) {
            require(!(orientation != HORIZONTAL && orientation != VERTICAL)) { "Invalid orientation. It should be either HORIZONTAL or VERTICAL" }
            mOrientation = orientation
        }

        /**
         * 一個app中分隔線不可能完全一樣，你可以通過這個方法傳遞一個Drawable 對象來定制分隔線
         */
        fun setDrawable(drawable: Drawable) {
            requireNotNull(drawable) { "Drawable cannot be null." }
            mDivider = drawable
        }

        /**
         * 画分隔线
         */
        override fun onDraw(
            c: Canvas,
            parent: RecyclerView,
            state: State
        ) {
            if (parent.layoutManager == null) {
                return
            }
            if (mOrientation == VERTICAL) {
                drawVertical(c, parent)
            } else {
                drawHorizontal(c, parent)
            }
        }

        /**
         * 在LinearLayoutManager方向為Vertical時，畫分隔線
         */
        private fun drawVertical(canvas: Canvas?, parent: RecyclerView) {
            val left = parent.paddingLeft //分隔線的左邊 = paddingLeft值
            val right = parent.width - parent.paddingRight //分隔線的右邊 = RecyclerView 寬度-paddingRight值
            //分隔線不在RecyclerView的padding那一部分繪製
            val childCount = parent.childCount //分隔線數量=item數量
            for (i in 0 until childCount) {
                val child = parent.getChildAt(i) //確定是第幾個item
                val params =
                    child.layoutParams as RecyclerView.LayoutParams
                val top =
                    child.bottom + params.bottomMargin //分隔線的上邊 = item的底部 + item根標籤的bottomMargin值
                val bottom = top + mDivider!!.intrinsicHeight //分隔線的下邊 = 分隔線的上邊 + 分隔線本身高度
                mDivider!!.setBounds(left, top, right, bottom)
                mDivider!!.draw(canvas!!)
            }
        }

        /**
         * 在LinearLayoutManager方向為Horizontal時，畫分隔線
         */
        private fun drawHorizontal(canvas: Canvas?, parent: RecyclerView) {
            val top = parent.paddingTop
            val bottom = parent.height - parent.paddingBottom

            val childCount = parent.childCount
            for (i in 0 until childCount) {
                val child = parent.getChildAt(i)
                val params =
                    child.layoutParams as RecyclerView.LayoutParams
                val left = child.right + params.rightMargin
                val right = left + mDivider!!.intrinsicHeight
                mDivider!!.setBounds(left, top, right, bottom)
                mDivider!!.draw(canvas!!)
            }
        }

        /**
         * 獲取Item偏移量
         * 此方法是為每個Item四周預留出空間，從而讓分隔線的繪製在預留的空間內
         */
        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView,
            state: State
        ) {
            if (mOrientation == VERTICAL) { //竖直方向的分隔线：item向下偏移一个分隔线的高度
                outRect[0, 0, 0] = mDivider!!.intrinsicHeight
            } else { //水平方向的分隔线：item向右偏移一个分隔线的宽度
                outRect[0, 0, mDivider!!.intrinsicWidth] = 0
            }
        }

        companion object {
            const val HORIZONTAL = LinearLayout.HORIZONTAL
            const val VERTICAL = LinearLayout.VERTICAL

            //使用系統主題中的R.attr.listDivider作為Item間的分隔線
            private val ATTRS = intArrayOf(android.R.attr.listDivider)
        }

        init {
//        val a = context.obtainStyledAttributes(ATTRS)
//        mDivider = a.getDrawable(0)
//        a.recycle()
            // 修改為自定義的分隔線類型
            val shapeDrawable = ShapeDrawable(RectShape())
            shapeDrawable.intrinsicHeight =
                DisplayUtility.convertDpToPixel(dividerHeight.toFloat(), context).toInt()
            shapeDrawable.paint.color = Color.BLACK;
            mDivider = shapeDrawable
            setOrientation(orientation)
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
            state: State
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

}

//class DividerItemDecoration(context: Context, dividerHeight: Int, itemSpaceMarginTop: Int) :
//    RecyclerView.ItemDecoration() {
//
//    private var mContext = context
//    private var mDividerHeight = 0f
//    private var mItemSpaceMarginTop = 0f
//    private var mPaint: Paint? = null
//
//    init {
//        mPaint = Paint()
//        mPaint?.isAntiAlias = true
//        mPaint?.color = Color.BLACK
//        mDividerHeight = TypedValue.applyDimension(
//            TypedValue.COMPLEX_UNIT_DIP,
//            dividerHeight.toFloat(),
//            mContext.resources.displayMetrics
//        )
//        mItemSpaceMarginTop = TypedValue.applyDimension(
//            TypedValue.COMPLEX_UNIT_DIP,
//            itemSpaceMarginTop.toFloat(),
//            mContext.resources.displayMetrics
//        )
//    }
//
//    override fun getItemOffsets(
//        outRect: Rect,
//        view: View,
//        parent: RecyclerView,
//        state: RecyclerView.State
//    ) {
//        super.getItemOffsets(outRect, view, parent, state)
//        if (parent.getChildAdapterPosition(view) != 0) {
//            // 第二個ItemView開始與上方的間距
//            outRect.top = mItemSpaceMarginTop.toInt()
//        }
//        val position = parent.getChildAdapterPosition(view)
//        // 更換背景顏色
////        view.setBackgroundColor(if (position % 2 == 0) ContextCompat.getColor(mContext, R.color.green) else ContextCompat.getColor(mContext, R.color.yellow))
//    }
//
//    // 繪製於背景之下 內容會在上方
//    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//
//    }
//
//    // 繪製於內容之上 內容會被覆蓋
//    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//        super.onDrawOver(c, parent, state)
//        val childCount = parent.childCount
//        for (i in 0 until childCount) {
//            val view = parent.getChildAt(i)
//            val index = parent.getChildAdapterPosition(view)
//            //第一個ItemView不需要繪製
//            if (index == 0) {
//                continue
//            }
//            val dividerTop = view.top - mDividerHeight
//            val dividerLeft = parent.paddingLeft.toFloat()
//            val dividerBottom = view.top.toFloat()
//            val dividerRight = (parent.width - parent.paddingRight).toFloat()
//            c.drawRect(dividerLeft, dividerTop, dividerRight, dividerBottom, mPaint!!)
//        }
//    }
//}
