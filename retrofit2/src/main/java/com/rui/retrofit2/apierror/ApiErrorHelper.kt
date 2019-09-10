package com.rui.mvvm.network.ApiErro

import android.content.Context
import android.widget.Toast
import com.rui.retrofit2.apierror.ApiErrorCode
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * api错误处理逻辑类，不同的数据源需要共用这个类，如果只有一种数据源可以直接在consumer后面处理；
 * Created by rui on 2018/7/31.
 */
object ApiErrorHelper {

    fun handleCommonError(context: Context, throwable: Throwable) {

        throwable.printStackTrace()

        if (throwable is ConnectException|| throwable is UnknownHostException) {
            Toast.makeText(
                context.applicationContext,
                "无网络连接,请检查网络状态是否正常", Toast.LENGTH_LONG
            ).show()
        } else if (throwable is SocketTimeoutException) {
            Toast.makeText(
                context.applicationContext,
                "服务连接超时", Toast.LENGTH_LONG
            ).show()
        } else if (throwable is HttpException) {
            val code = throwable.code()
            Toast.makeText(
                context.applicationContext,
                String.format("服务不可用，请稍后重试(%d)", code), Toast.LENGTH_LONG
            ).show()
        } else if (throwable is ApiException) {
            //ApiException处理
            val code = throwable.errorCode
            when (code) {
                ApiErrorCode.ERROR_CLIENT_AUTHORIZED -> {
                    //handle
                }
                ApiErrorCode.ERROR_USER_AUTHORIZED -> {
                    //handle
                }
                ApiErrorCode.ERROR_REQUEST_PARAM -> {
                    //handle
                }
                ApiErrorCode.ERROR_PARAM_CHECK -> {
                    //handle
                }
                ApiErrorCode.ERROR_OTHER -> {
                    //handle
                }
                else -> Toast.makeText(
                    context.applicationContext,
                    "Api未知错误:"+throwable.message, Toast.LENGTH_LONG
                ).show()
            }
        } else {
            Toast.makeText(
                context.applicationContext,
                "未知错误："+throwable.message, Toast.LENGTH_LONG
            ).show()
        }
    }

}
