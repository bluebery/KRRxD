package com.hootsuite.krrxd.mvvm

import android.util.Log
import com.hootsuite.krrxd.CurrentUsersRecyclerAdapter
import com.hootsuite.krrxd.persistence.User
import com.hootsuite.krrxd.persistence.UserDao
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginViewModel @Inject constructor() : ListViewModel<List<User>> {

    @Inject
    lateinit var userDao: UserDao

    // todo transform the users for display, only emit items that are directly displayable in the adapter
    override val flowable: Flowable<List<User>>
        get() = userDao.getAllUsers()
}