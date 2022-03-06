package com.rui.mvvm.dialogfragment

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.rui.mvvm.BR
import com.rui.mvvm.vmodel.BaseViewModel

/**
 *Created by rui on 2019/8/2
 */
open abstract class BaseVMDialogFragment<DB : ViewDataBinding, VM : BaseViewModel>
    : BaseDBDialogFragment<DB>() {

    protected lateinit var viewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = obtainViewModel(getVMClass())
        bindingVM()
    }

    /**
     * 获取ViewModelClass
     *
     * @return
     */
    protected abstract fun getVMClass(): Class<VM>

    /**
     * 绑定ViewModel,此方法可以扩展绑定
     */
    protected fun bindingVM() {
        binding.setVariable(BR.viewModel, viewModel)
    }

    /**
     * 获取ViewModel实例
     *
     * @param modelClass
     * @return
     */
    abstract fun obtainViewModel(modelClass: Class<VM>): VM


}

