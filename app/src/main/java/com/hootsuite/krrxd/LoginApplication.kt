package com.hootsuite.krrxd

import com.hootsuite.krrxd.dagger.AppModule
import com.hootsuite.krrxd.dagger.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class LoginApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
}