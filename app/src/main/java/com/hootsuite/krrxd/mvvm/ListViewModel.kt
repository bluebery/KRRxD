package com.hootsuite.krrxd.mvvm

import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject

interface ListViewModel<T> {
    var results: BehaviorSubject<T>
}