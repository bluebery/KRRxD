package com.hootsuite.krrxd.mvvm

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import com.hootsuite.krrxd.CurrentUsersRecyclerAdapter
import com.hootsuite.krrxd.persistence.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

class UsersBindingRecyclerView: BindingRecyclerView<List<User>> {

    // used when given in code or xml
    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
        RecyclerView(context, attrs, 0)
    }

    // used if given a style in xml
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        RecyclerView(context, attrs, defStyleAttr)
    }

    var disposable : Disposable? = null

    override fun setup(listViewModel: ListViewModel<List<User>>) {
        disposable = listViewModel.flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            (adapter as CurrentUsersRecyclerAdapter).users = it
                        },
                        { throwable ->
                            Log.d("LoginActivity", throwable.message)
                        },
                        {
                            Log.d("LoginActivity", "completed get all")
                        })
    }
}