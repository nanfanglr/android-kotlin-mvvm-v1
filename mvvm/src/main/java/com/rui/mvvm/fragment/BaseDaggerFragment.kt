package com.rui.mvvm.fragment


import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import com.rui.mvvm.vmodel.BaseViewModel
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 *Created by rui on 2019/8/2
 */
open abstract class BaseDaggerFragment<DB : ViewDataBinding, VM : BaseViewModel>
    : BaseLazyVMFragment<DB, VM>(), HasAndroidInjector {

    @Inject
    internal lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    /**
     * 获取ViewModel实例
     *
     * @param modelClass
     * @return
     */
    override fun obtainViewModel(modelClass: Class<VM>): VM {
        return ViewModelProviders.of(this, viewModelFactory).get(modelClass)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(requireContext())
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }
}

