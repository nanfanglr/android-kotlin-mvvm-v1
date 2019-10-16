package com.rui.kotlin_mvvm.di

import com.rui.kotlin_mvvm.netservice.NetService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 *Created by rui on 2019/8/2
 */
@Module
internal  class SingleTonModule {

    @Provides
    @Singleton
    internal fun providesNetService(retrofit: Retrofit): NetService {
        return retrofit.create(NetService::class.java)
    }

}