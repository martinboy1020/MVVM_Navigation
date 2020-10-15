package com.example.base.rigger

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.base.components.AnnotationParse

open abstract class FragmentRigger: Fragment(){

    val binding by lazy {
        Log.d("tag12345", "AnnotationParse.getAnnotatedLayout(this): " + AnnotationParse.getAnnotatedLayout(this))
        val viewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(this.context), AnnotationParse.getAnnotatedLayout(this), null, false)
        Log.d("tag12345", "FragmentRigger viewDataBinding: " + viewDataBinding)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = this.binding.root


}