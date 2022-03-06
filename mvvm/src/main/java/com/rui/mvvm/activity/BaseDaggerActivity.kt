package com.rui.mvvm.activity

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.rui.mvvm.vmodel.BaseViewModel
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 *Created by rui on 2019/8/1
 */
open abstract class BaseDaggerActivity<DB : ViewDataBinding, VM : BaseViewModel>
    : BaseVMActivity<DB, VM>(), HasAndroidInjector {

    /**
     * 当前页面的ViewModel实例工厂
     */
    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    protected lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    /**
     * 获取ViewModel实例
     *
     * @param modelClass
     * @return
     */
    override fun obtainViewModel(modelClass: Class<VM>): VM {
        return ViewModelProviders.of(this, viewModelFactory).get(modelClass)
    }


    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }


}