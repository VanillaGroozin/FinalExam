package com.example.finalexam.domain.base

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

abstract class BaseNetworkUseCase<T> {
    val compositeDisposable = CompositeDisposable()

    abstract fun initiateCreateObservableByQuery(query: String): Observable<T>

    fun clear() {
        compositeDisposable.clear()
    }
}