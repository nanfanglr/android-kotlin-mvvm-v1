package com.rui.retrofit2.basemodel

/**
 * Created by rui on 2018/3/9.
 */
class ResultModel<T> : BaseResultModel() {

    var obj: T? = null

    var data: List<T> = ArrayList()

}
