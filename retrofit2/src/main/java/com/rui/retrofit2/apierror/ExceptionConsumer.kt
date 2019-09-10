package com.rui.retrofit2.apierror

import android.content.Context
import com.rui.mvvm.network.ApiErro.ApiErrorHelper
import io.reactivex.functions.Consumer


/**
 *Created by rui on 2019/8/21
 */
class ExceptionConsumer(private val context: Context) : Consumer<Throwable> {

    override fun accept(t: Throwable) {
        t.printStackTrace()
        ApiErrorHelper.handleCommonError(context, t)
    }

}