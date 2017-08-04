package com.hootsuite.krrxd.mvvm

import com.jakewharton.rxrelay2.BehaviorRelay

interface ListViewModel<T> : ViewModel {
    val results: BehaviorRelay<T>
}