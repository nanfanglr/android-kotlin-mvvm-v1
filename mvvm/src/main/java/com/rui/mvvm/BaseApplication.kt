package com.rui.mvvm

import android.support.multidex.MultiDexApplication
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

/**
 *Created by rui on 2019/8/1
 */
abstract class BaseApplication : MultiDexApplication(), HasAndroidInjector {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        injectIfNecessary()
        setupLeakCanary()
    }

    /**
     * 初始化日志打印相关
     */
    fun initTimber() {
        if (isDebug()) Timber.plant(Timber.DebugTree())
    }

    abstract fun isDebug(): Boolean

    /**
     * 子类必须实现并传入applicationInjector
     */
    protected abstract fun applicationInjector(): AndroidInjector<out BaseApplication>

    /**
     * Kotlin中使用Dagger2 可能导致错误"Dagger does not support injection into private fields"
     * Kotlin生成.java文件时属性默认为private，给属性添加@JvmField声明可以转成public
     */
    @Inject
    @JvmField
    var androidInjector: DispatchingAndroidInjector<Any>? = null

    override fun androidInjector(): AndroidInjector<Any>? {
        // injectIfNecessary should already be called unless we are about to inject a ContentProvider,
        // which can happen before Application.onCreate()
        injectIfNecessary()
        return androidInjector
    }

    private fun injectIfNecessary() {
        if (androidInjector == null) {
            synchronized(this) {
                if (androidInjector == null) {
                    val applicationInjector = applicationInjector() as AndroidInjector<BaseApplication>
                    applicationInjector.inject(this)
                    if (androidInjector == null) {
                        throw IllegalStateException(
                            "The AndroidInjector returned from applicationInjector() did not inject the " + "BaseApplication"
                        )
                    }
                }
            }
        }
    }

    private fun setupLeakCanary() {
        if (isDebug()) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return
            }
            LeakCanary.install(this)
        }
    }

}
