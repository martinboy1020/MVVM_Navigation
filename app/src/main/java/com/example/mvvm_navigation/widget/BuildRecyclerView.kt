package com.example.mvvm_navigation.widget

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_navigation.R

class BuildRecyclerView constructor(context: Context, val attributeSet: AttributeSet? = null): RecyclerView(context, attributeSet){

    init {
        init()
    }

    enum class ManagerType(val code: Int){
        HORIZONTAL(0), VERTICAL(1), GRID(2)
    }

    private fun init(){
        val typedArray = this.context.obtainStyledAttributes(this.attributeSet, R.styleable.BuildRecyclerView)
        val managerType = typedArray.getInt(R.styleable.BuildRecyclerView_layout_manager_type, 0)
        val column = typedArray.getInt(R.styleable.BuildRecyclerView_column, 0)
        this.layoutManager =
            when(managerType){
                ManagerType.GRID.code -> GridLayoutManager(this.context, column)
                else -> LinearLayoutManager(this.context, managerType, false)
            }
        typedArray.recycle()
    }
}