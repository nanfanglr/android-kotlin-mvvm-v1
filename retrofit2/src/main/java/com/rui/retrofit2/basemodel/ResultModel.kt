package com.rui.retrofit2.basemodel

/**
 * Created by rui on 2018/3/9.
 */
class ResultModel<T> : BaseResultModel() {

    var obj: T? = null

    var data: List<T> = ArrayList()

    var pageData: PageData<T> = PageData()

//    fun getData(): List<*> {
//        return data
//    }
//
//    fun setData(data: List<*>) {
//        this.data = data
//    }

    class PageData<T> {
        var list: List<T> = ArrayList()
    }

}
