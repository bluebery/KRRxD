package com.hootsuite.krrxd.mvvm

import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import com.hootsuite.krrxd.persistence.User
import com.hootsuite.krrxd.persistence.UserDao
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginListViewModel @Inject constructor(val userDao: UserDao) : ListViewModel<List<User>> {

    override val results: BehaviorRelay<List<User>> = BehaviorRelay.create()

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun create() {
        // https://medium.com/google-developers/room-rxjava-acb0cd4f3757
        // Now, every time the user records are updated, our Flowable object will emit automatically, allowing you to update the UI based on the latest data.
        // The Flowable will emit only when the query result contains at least a row.
        // When there is no data to match the query, the Flowable will not emit, neither onNext nor onError.
        compositeDisposable.add(
                userDao
                        .getAllUsers()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    results.accept(it)
                                },
                                { throwable ->
                                    Log.d("LoginViewModel", throwable.message)
                                },
                                {
                                    Log.d("LoginViewModel", "completed get all")
                                }))
    }

    override fun dispose() {
        compositeDisposable.clear()
    }

    internal fun signIn(email: String, password: String, userMessage: ((String) -> Unit)? = null) {
        compositeDisposable.add(
                Maybe
                        .fromCallable { userDao.getUser(email) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { user ->
                                    userMessage?.invoke(if (user.password == password) "Login successful" else "Incorrect password")
                                },
                                { throwable ->
                                    Log.d("LoginActivity", throwable.message)
                                },
                                {
                                    userMessage?.invoke("User does not exist")
                                }))
    }

    internal fun register(email: String, password: String, userMessage: ((String) -> Unit)? = null) {
        compositeDisposable.add(
                Completable
                        .fromCallable { userDao.insertUser(User(userName = email, password = password)) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    userMessage?.invoke("Registered!")
                                },
                                { throwable ->
                                    Log.d("LoginActivity", throwable.message)
                                }))
    }

    fun clearUsers() {
        compositeDisposable.add(Completable
                .fromCallable { userDao.deleteAllUsers() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { Log.d("LoginActivity", "deleted users") },
                        { throwable -> Log.d("LoginActivity", throwable.message) }
                ))
    }
}