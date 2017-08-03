package com.hootsuite.krrxd.mvvm

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

abstract class BindingRecyclerView<T>: RecyclerView {

    // used when given in code or xml
    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
        RecyclerView(context, attrs, 0)
    }

    // used if given a style in xml
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        RecyclerView(context, attrs, defStyleAttr)
    }

    abstract fun setup(listViewModel: ListViewModel<T>)
}