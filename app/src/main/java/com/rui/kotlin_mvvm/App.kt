package com.rui.kotlin_mvvm


import com.rui.kotlin_mvvm.di.DaggerApplicationComponent
import com.rui.mvvm.BaseApplication
import dagger.android.AndroidInjector

/**
 *Created by rui on 2019/8/2
 */
class App : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        //初始化数据库

    }

    override fun applicationInjector(): AndroidInjector<out BaseApplication> {
        return DaggerApplicationComponent.factory().create(this)
    }

    override fun isDebug(): Boolean {
        return BuildConfig.DEBUG
    }
}