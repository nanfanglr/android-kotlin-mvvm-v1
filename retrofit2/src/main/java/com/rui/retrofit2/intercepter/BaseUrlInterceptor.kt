package com.rui.retrofit2.intercepter

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

/**
 *Created by rui on 2019/8/5
 */
class BaseUrlInterceptor constructor(realBaseUrl: String) : Interceptor {

    private val realBaseUrl: String

    @Volatile
    var host: String? = null

    init {
        this.realBaseUrl = realBaseUrl;
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (host != null && realBaseUrl != host) {
            val newUrl = HttpUrl.parse(host!!)
            request = request.newBuilder()
                .url(newUrl!!)
                .build()
        }
        return chain.proceed(request)
    }
}