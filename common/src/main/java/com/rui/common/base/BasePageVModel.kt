package com.rui.common.base

import androidx.databinding.ObservableInt
import com.rui.common.ConstantVal
import com.rui.mvvm.BaseApplication
import com.rui.mvvm.Event
import com.rui.mvvm.network.ApiErro.ApiErrorHelper

/**
 *Created by rui on 2019/8/8
 */
abstract class BasePageVModel<ITEM>(app: BaseApplication) : BaseListVModel<ITEM>(app) {

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
        addSubscribe(
            getDataOB().compose(singleTransformer(loadRefresh)).subscribe(
                {
                    if (it.success) {
                        if (page == 1) {
                            items.clear()
                            items.addAll(it.data)
                        } else {
                            items.addAll(it.data)
                        }
                        total.set(it.total)
                        sumPage = it.getSumPage(ConstantVal.LIMIT)
                        page++
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


}