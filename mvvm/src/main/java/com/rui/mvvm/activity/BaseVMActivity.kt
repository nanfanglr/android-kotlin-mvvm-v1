package com.rui.mvvm.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import com.rui.mvvm.BR
import com.rui.mvvm.EventObserver
import com.rui.mvvm.vmodel.BaseViewModel

/**
 *Created by rui on 2019/8/1
 */
open abstract class BaseVMActivity<DB : ViewDataBinding, VM : BaseViewModel>
    : BaseDBActivity<DB>() {
    /**
     * 当前页面的ViewModel
     */
    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = obtainViewModel(getVMClass())
        bindingVM()
    }

    /**
     * 获取ViewModel实例
     *
     * @param modelClass
     * @return
     */
    protected abstract fun obtainViewModel(modelClass: Class<VM>): VM

    /**
     * 获取ViewModelClass
     *
     * @return
     */
    protected abstract fun getVMClass(): Class<VM>

    /**
     * 绑定ViewModel,此方法可以扩展绑定
     */
    protected open fun bindingVM() {
        binding.setVariable(BR.viewModel, viewModel)
    }



}