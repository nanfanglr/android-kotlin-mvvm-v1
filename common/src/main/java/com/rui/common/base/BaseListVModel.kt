package com.rui.common.base


import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.rui.common.ConstantVal
import com.rui.mvvm.BaseApplication
import com.rui.mvvm.Event
import com.rui.mvvm.network.ApiErro.ApiErrorHelper
import com.rui.mvvm.vmodel.BaseViewModel
import com.rui.retrofit2.basemodel.ResultModel
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *Created by rui on 2019/8/8
 */
abstract class BaseListVModel<ITEM >(app: BaseApplication) : BaseViewModel(app) {
    /**
     * 数据集
     */
    val items = ObservableArrayList<ITEM>()
    /**
     * 下拉刷新完成
     */
    val finishRefreshing = ObservableBoolean()
    /**
     * 上拉加载完成
     */
    val finishLoadmore = ObservableBoolean()
    /**
     * 没有更多数据了
     */
    val loadNoMoreData = ObservableBoolean()
    /**
     * 没有列表数据通知事件
     */
    val empty = MutableLiveData<Event<Unit>>()
    /**
     * 列表为空的提示文案
     */
    val emptyText = ObservableField<String>()

    /**
     * 存在下拉刷新需求是调用这个方法，否则调用上一个基类singleTransformer无参数的方法
     *
     * @param loadRefresh
     * @param <T>
     * @return
    </T> */
    protected fun <T> singleTransformer(loadRefresh: Int): SingleTransformer<T, T> {
        return SingleTransformer { apiResultObservable ->
            apiResultObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _ -> finishRefreshOrLoadMore(loadRefresh, false) }
                .doAfterTerminate { finishRefreshOrLoadMore(loadRefresh, true) }
        }
    }

    /**
     * 结束下拉刷新或加载更多动画效果
     *
     * @param loadRefresh
     * @param aboolean
     */
    protected fun finishRefreshOrLoadMore(loadRefresh: Int, aboolean: Boolean) {
        if (loadRefresh == ConstantVal.LOAD_REFRESH) {
            finishRefreshing.set(aboolean)
            //下拉刷新重置loadNoMoreData的状态
            if (aboolean) loadNoMoreData.set(false)
        } else if (loadRefresh == ConstantVal.LOAD_MORE) {
            finishLoadmore.set(aboolean)
        } else if (loadRefresh == ConstantVal.LOAD_FIRST) {
            dataLoading.value = Event(!aboolean)
        }
    }

    open fun getData(loadRefresh: Int) {
        loadNoMoreData.set(true)
        addSubscribe(
            getDataOB()
                .compose(singleTransformer(loadRefresh))
                .subscribe(
                    {
                        if (it.success) {
                            items.clear()
                            items.addAll(it.data)
                            calculateTotal()
                            if (items.size == 0) empty.value = Event(Unit)
                        } else {
                            dataLoadingError.value = Event(it.msg)
                        }
                    },
                    {
                        ApiErrorHelper.handleCommonError(app, it)
                    })
        )
    }

    abstract fun getDataOB(): Single<ResultModel<ITEM>>
    /**
     * 计算数组item某些属性的总和，根据需要重写此方法即可
     */
    open fun calculateTotal() {}

}