package com.rui.mvvm.dialogfragment

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
 *Created by rui on 2019/9/3
 */
/**
 *Created by rui on 2019/8/2
 */
open abstract class BaseDaggerDialogFragment<DB : ViewDataBinding, VM : BaseViewModel>
    : BaseVMDialogFragment<DB, VM>(), HasAndroidInjector {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    internal lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
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



}