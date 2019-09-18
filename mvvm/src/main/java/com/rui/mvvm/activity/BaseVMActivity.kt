package com.rui.mvvm.activity

import android.app.ProgressDialog
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.widget.Toast
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

    private val _processBar: ProgressDialog by lazy {
        ProgressDialog(
            this,
            ProgressDialog.THEME_HOLO_LIGHT
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = obtainViewModel(getVMClass())
        bindingVM()
        setLoadingBar()
        setErrorHint()
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

    /**
     * 如果该页面不需要统一的loading效果可以重写此方法
     */
    protected open fun setLoadingBar() {
        viewModel.dataLoading.observe(this, EventObserver {
            if (it) showProcessBar(getLoadingText()) else _processBar.cancel()
        })
    }

    protected open fun showProcessBar(message: String) {
        if (_processBar.isShowing) {
            _processBar.dismiss()
        }
        with(_processBar) {
            setMessage(message)
            show()
            setCanceledOnTouchOutside(false)
            setCancelable(true)
        }
    }

    /**
     * 重写此方法改变loading效果的文案
     *
     * @return
     */
    protected open fun getLoadingText(): String {
        return "正在获取信息，请稍候！"
    }

    /**
     * 加载错误统一提示，如果不需要就重写此方法
     */
    protected open fun setErrorHint() {
        viewModel.dataLoadingError.observe(this, EventObserver {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

}