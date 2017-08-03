package com.hootsuite.krrxd

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.hootsuite.krrxd.mvvm.LoginViewModel
import com.hootsuite.krrxd.persistence.User
import com.hootsuite.krrxd.persistence.UserDao
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var userDao: UserDao
    @Inject
    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // kotlin: property access
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = CurrentUsersRecyclerAdapter(this)

        // With Maybe, unlike Flowable, if there is only a single value to be signalled, only onSuccess is called and onComplete is not.
        email_sign_in_button.setOnClickListener {
            Maybe
                    .fromCallable { userDao.getUser(email.text.toString()) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { user ->
                                Snackbar
                                        .make(
                                                email_login_form,
                                                if (user.password == password.text.toString()) "Login successful" else "Incorrect password",
                                                Snackbar.LENGTH_LONG)
                                        .show()
                            },
                            { throwable -> Log.d("LoginActivity", throwable.message) },
                            { Snackbar.make(email_login_form, "User does not exist", Snackbar.LENGTH_LONG).show() })
        }

        // kotlin: lambdas
        email_register_button.setOnClickListener {
            Completable
                    .fromCallable { userDao.insertUser(User(userName = email.text.toString(), password = password.text.toString())) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { Snackbar.make(email_login_form, "Registered!", Snackbar.LENGTH_LONG).show() },
                            { throwable -> Log.d("LoginActivity", throwable.message) })
        }

        clear_button.setOnClickListener {
            Completable
                    .fromCallable { userDao.deleteAllUsers() }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { Log.d("LoginActivity", "deleted users") },
                            { throwable -> Log.d("LoginActivity", throwable.message) }
                    )

            Log.d("LoginActivity", "isDisposed " + recycler_view.disposable?.isDisposed)
        }

        // https://medium.com/google-developers/room-rxjava-acb0cd4f3757
        // Now, every time the user records are updated, our Flowable object will emit automatically, allowing you to update the UI based on the latest data.
        // The Flowable will emit only when the query result contains at least a row.
        // When there is no data to match the query, the Flowable will not emit, neither onNext nor onError.
//        userDao.getAllUsers()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        { (recycler_view.adapter as CurrentUsersRecyclerAdapter).users = it },
//                        { throwable -> Log.d("LoginActivity", throwable.message) },
//                        { Log.d("LoginActivity", "completed get all") })

        recycler_view.setup(loginViewModel)

    }
}