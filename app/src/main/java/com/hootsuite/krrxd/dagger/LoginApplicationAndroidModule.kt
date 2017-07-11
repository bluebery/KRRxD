package com.hootsuite.krrxd.dagger

import com.hootsuite.krrxd.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * If you add provides methods to this module, you will need to refactor the [@ContributesAndroidInjector] methods into their own module.
 */
@Module
@Suppress("unused")
abstract class LoginApplicationAndroidModule {

    @ContributesAndroidInjector()
    abstract fun loginActivity(): LoginActivity
}
