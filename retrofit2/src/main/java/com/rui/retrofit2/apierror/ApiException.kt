package com.rui.mvvm.network.ApiErro

/**
 * 自定义的api错误类，以方便统一处理
 * Created by rui on 2018/7/31.
 */
class ApiException(val errorCode: Int, msg: String) : RuntimeException(msg)