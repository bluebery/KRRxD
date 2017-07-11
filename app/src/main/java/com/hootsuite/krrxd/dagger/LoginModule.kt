package com.hootsuite.krrxd.dagger

import dagger.Module

@Module(includes = arrayOf(LoginApplicationAndroidModule::class))
abstract class LoginModule