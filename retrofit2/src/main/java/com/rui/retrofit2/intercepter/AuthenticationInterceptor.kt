package com.rui.retrofit2.intercepter

import com.rui.retrofit2.PropertiesManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 *Created by rui on 2019/8/5
 */
class AuthenticationInterceptor @Inject constructor(private val propertiesManager: PropertiesManager) : Interceptor {
    private val API_AUTH_TOKEN = "api_auth_token"

//    private val propertiesManager: PropertiesManager

//    init {
//        this.propertiesManager = propertiesManager;
//    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .addHeader(API_AUTH_TOKEN, propertiesManager.apiToken)
            .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
            .addHeader("Accept-Encoding", "gzip, deflate")
            .addHeader("Connection", "keep-alive")
            .addHeader("Accept", "*/*")
            .build()
        return chain.proceed(request)

    }
}