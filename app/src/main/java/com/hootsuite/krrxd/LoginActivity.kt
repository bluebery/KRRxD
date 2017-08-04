package com.hootsuite.krrxd

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import com.hootsuite.krrxd.mvvm.LoginListViewModel
import com.hootsuite.krrxd.mvvm.ViewModel
import com.hootsuite.krrxd.persistence.UserDao
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit internal var userDao: UserDao
    @Inject
    lateinit internal var loginListViewModel: LoginListViewModel

    val viewModels: MutableList<ViewModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = CurrentUsersRecyclerAdapter(this)

        bindLoginListViewModel().also { viewModels.add(it) }

        viewModels.forEach { it.create() }
    }

    override fun onDestroy() {
        viewModels.forEach { it.dispose() }
        super.onDestroy()
    }

    private fun bindLoginListViewModel(): LoginListViewModel {

        // With Maybe, unlike Flowable, if there is only a single value to be signalled, only onSuccess is called and onComplete is not.
        email_sign_in_button.setOnClickListener {
            loginListViewModel.signIn(email = email.text.toString(), password = password.text.toString()) { displayMessage(it) }
        }

        email_register_button.setOnClickListener {
            loginListViewModel.register(email = email.text.toString(), password = password.text.toString()) { displayMessage(it) }
        }

        clear_button.setOnClickListener { loginListViewModel.clearUsers() }

        return loginListViewModel.also { recycler_view.setup(it) }
    }

    private fun displayMessage(message: String) {
        Snackbar.make(email_login_form, message, Snackbar.LENGTH_LONG).show()
    }
}