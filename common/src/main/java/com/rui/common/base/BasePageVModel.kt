package com.rui.common.base

import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.rui.common.ConstantVal
import com.rui.mvvm.BaseApplication
import com.rui.mvvm.Event
import com.rui.mvvm.network.ApiErro.ApiErrorHelper
import timber.log.Timber

/**
 *Created by rui on 2019/8/8
 */
abstract class BasePageVModel<ITEM>(app: BaseApplication) : BaseListVModel<ITEM>(app) {
    /**
     * 数据集
     */
    val items1 = MutableLiveData<MutableList<ITEM>?>()

    /**
     * 总数量
     */
    var total = ObservableInt()

    /**
     * 数据页码（第几页数据）
     */
    protected var page = 1

    /**
     * 总计页数
     */
    protected var sumPage: Int = 0


    override fun getData(loadRefresh: Int) {
        if (loadRefresh <= ConstantVal.LOAD_REFRESH) {
            page = 1
            sumPage = 100
        }
        if (page > sumPage) {
            loadNoMoreData.set(true)
            return
        }
        Timber.d("-------->getData1")
        addSubscribe(
            getDataOB().compose(singleTransformer(loadRefresh)).subscribe(
                {
                    Timber.d("-------->getData2")
                    if (it.success) {
                        if (page == 1) {
                            Timber.d("-------->getData3")
                            items1.value = it.data.toMutableList()
                        } else {
                            Timber.d("-------->getData4")
                            items1.value?.addAll(it.data)
                            items1.value = items1.value
                        }
                        total.set(it.total)
                        sumPage = it.getSumPage(ConstantVal.LIMIT)
                        page++
                        if (items.value?.isEmpty() == true) empty.value = Event(Unit)
                    } else {
                        dataLoadingError.value = Event(it.msg)
                    }
                },
                {
                    ApiErrorHelper.handleCommonError(app, it)
                })
        )
    }


}