package com.hootsuite.krrxd.mvvm

import io.reactivex.Flowable

interface ListViewModel<T> {
    val flowable: Flowable<T>
}