package com.hootsuite.krrxd.dagger

import com.hootsuite.krrxd.LoginApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(modules = arrayOf(
        AndroidSupportInjectionModule::class,
        AppModule::class,
        LoginModule::class))
@Singleton
@Suppress("unused")
interface AppComponent : AndroidInjector<LoginApplication>