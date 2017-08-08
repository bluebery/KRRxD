package com.hootsuite.krrxd

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.hootsuite.krrxd.mvvm.LoginListViewModel
import com.hootsuite.krrxd.persistence.AppDatabase
import com.hootsuite.krrxd.persistence.User
import com.hootsuite.krrxd.persistence.UserDao
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class LoginListViewModelTest {

    lateinit private var userDao: UserDao
    lateinit private var appDatabase: AppDatabase
    lateinit private var loginListViewModel: LoginListViewModel

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        userDao = appDatabase.userDao()

        loginListViewModel = LoginListViewModel(userDao, Schedulers.trampoline())
        loginListViewModel.create()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        appDatabase.close()
    }

    @Test
    fun subscribeResults_shouldEmitUser_AfterRegister() {

        // setup
        val user = User(userName = "some userName", password = "password")
        val observer = mockObserver<List<User>>()
        loginListViewModel.results.subscribe(observer)

        // run
        loginListViewModel.register(user = user)

        // verify
        verify(observer, times(1)).onNext(emptyList())
        verify(observer, times(1)).onNext(listOf(user))
        verify(observer, never()).onError(any(Throwable::class.java))
        verify(observer, never()).onComplete()
    }

    @Suppress("unchecked_cast")
    fun <T> mockObserver(): io.reactivex.Observer<T> {
        return mock(io.reactivex.Observer::class.java) as io.reactivex.Observer<T>
    }
}