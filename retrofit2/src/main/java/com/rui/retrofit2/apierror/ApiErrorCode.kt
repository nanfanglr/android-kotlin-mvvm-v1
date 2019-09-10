package com.rui.retrofit2.apierror

/**
 * 定义APP相关网络错误常量
 * Created by rui on 2018/7/31.
 */
interface ApiErrorCode {
    companion object {
        /**
         * 客户端错误
         */
        val ERROR_CLIENT_AUTHORIZED = 1
        /**
         * 用户授权失败
         */
        val ERROR_USER_AUTHORIZED = 2
        /**
         * 请求参数错误
         */
        val ERROR_REQUEST_PARAM = 3
        /**
         * 参数检验不通过
         */
        val ERROR_PARAM_CHECK = 4
        /**
         * 自定义错误
         */
        val ERROR_OTHER = 10
        /**
         * 无网络连接
         */
        val ERROR_NO_INTERNET = 11

        /**
         * 无网络连接
         */
        val ERROR_NO_DATA = 12
    }
}
