/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rui.retrofit2.module

import android.content.res.AssetManager
import com.rui.retrofit2.PropertiesManager
import com.rui.retrofit2.intercepter.AuthenticationInterceptor
import com.rui.retrofit2.intercepter.BaseUrlInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class RetrofitModule {

    private val TIMEOUT_CONNECT = (60 * 1000).toLong()

    @Provides
    @Singleton
    internal fun providesHttpLoggingInterceptor(propertiesManager: PropertiesManager): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        if (propertiesManager.isDebug) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return interceptor
    }

    @Provides
    @Singleton
    internal fun providesPropertyManager(assetManager: AssetManager, @Named("debug") isDebug: Boolean): PropertiesManager {
        return PropertiesManager(assetManager, isDebug)
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(
        propertiesManager: PropertiesManager, httpLoggingInterceptor: HttpLoggingInterceptor,
        baseUrlInterceptor: BaseUrlInterceptor
    ): OkHttpClient {

        val okHttpBuilder = OkHttpClient.Builder()

        okHttpBuilder.connectTimeout(TIMEOUT_CONNECT, TimeUnit.MILLISECONDS)
        okHttpBuilder.writeTimeout(TIMEOUT_CONNECT, TimeUnit.MILLISECONDS)
        okHttpBuilder.readTimeout(TIMEOUT_CONNECT, TimeUnit.MILLISECONDS)

        // Adds authentication headers when required in network calls
        okHttpBuilder.addInterceptor(AuthenticationInterceptor(propertiesManager))

        // Helps with changing base url of network calls in espresso tests to the MockWebServer base url.
        okHttpBuilder.addInterceptor(baseUrlInterceptor)

        // Logs network calls for debug builds
        okHttpBuilder.addInterceptor(httpLoggingInterceptor)

        return okHttpBuilder.build()
    }

    @Provides
    @Singleton
    internal fun providesRetrofit(okHttpClient: OkHttpClient, propertiesManager: PropertiesManager): Retrofit {
        return Retrofit.Builder()
            .baseUrl(propertiesManager.getBaseUrl())
            .validateEagerly(propertiesManager.isDebug)// Fail early: check Retrofit configuration at creation time in Debug build.
            .addConverterFactory(GsonConverterFactory.create())
            //                .addConverterFactory(MyGsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    //    @ForTestingPurposes
    internal
    fun providesBaseUrlInterceptor(propertiesManager: PropertiesManager): BaseUrlInterceptor {
        return BaseUrlInterceptor(propertiesManager.getBaseUrl())
    }
}
