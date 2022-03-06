package com.rui.mvvm.vmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rui.mvvm.BaseApplication
import com.rui.mvvm.Event
import io.reactivex.FlowableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 *Created by rui on 2019/8/1
 */
abstract class BaseViewModel(protected var app: BaseApplication) : ViewModel() {

    val dataLoading = MutableLiveData<Event<Boolean>>()
//    val dataLoading: LiveData<Event<Boolean>> = dataLoading

    val dataLoadingError = MutableLiveData<Event<String>>()
//    val dataLoadingError: LiveData<Event<String>> = dataLoadingError

    /**
     * Rxjava订阅池
     */
    private var mCompositeDisposable = CompositeDisposable()

    /**
     * 添加Rxjava订阅到容器
     *
     * @param disposable
     */
    protected fun addSubscribe(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }


    override fun onCleared() {
        super.onCleared()
        //ViewModel销毁时会执行，同时取消所有异步任务，防止viewmodel泄漏
        mCompositeDisposable.clear()
    }

    protected fun <T> singleTransformer(): SingleTransformer<T, T> {
        return SingleTransformer { apiResultObservable ->
            apiResultObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _ -> dataLoading.value = Event(true) }
                .doAfterTerminate { dataLoading.value = Event(false) }
        }
    }

    protected fun <T> observableTransformer(): ObservableTransformer<T, T> {
        return ObservableTransformer { apiResultObservable ->
            apiResultObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _ -> dataLoading.value = Event(true) }
                .doAfterTerminate { dataLoading.value = Event(false) }
        }
    }


    protected fun <T> flowaleTransformer(): FlowableTransformer<T, T> {
        return FlowableTransformer { apiResultObservable ->
            apiResultObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _ -> dataLoading.value = Event(true) }
                .doAfterTerminate { dataLoading.value = Event(false) }
        }
    }



}