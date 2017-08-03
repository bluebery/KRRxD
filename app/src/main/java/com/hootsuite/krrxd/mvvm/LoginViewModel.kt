package com.hootsuite.krrxd.mvvm

import android.util.Log
import com.hootsuite.krrxd.CurrentUsersRecyclerAdapter
import com.hootsuite.krrxd.persistence.User
import com.hootsuite.krrxd.persistence.UserDao
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginViewModel @Inject constructor(val userDao: UserDao) : ListViewModel<List<User>> {

    override var results: BehaviorSubject<List<User>> = BehaviorSubject.create()

//    override val results: List<User> = listOf()
//        get() = field

    init {
        userDao.getAllUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            results.onNext(it)
                        },
                        { throwable ->
                            Log.d("LoginViewModel", throwable.message)
                        },
                        {
                            Log.d("LoginViewModel", "completed get all")
                        })
    }

    // todo transform the users for display, only emit items that are directly displayable in the adapter
    override val flowable: Flowable<List<User>>
        get() = userDao.getAllUsers()
}