package com.rui.mvvm.fragment

import android.app.ProgressDialog
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.rui.mvvm.BR
import com.rui.mvvm.EventObserver
import com.rui.mvvm.vmodel.BaseViewModel
import timber.log.Timber

/**
 *Created by rui on 2019/8/2
 */
open abstract class BaseLazyVMFragment<DB : ViewDataBinding, VM : BaseViewModel>
    : BaseDBFragment<DB>() {

    /**
     * 标识fragment视图已经初始化完毕
     */
    protected var isViewPrepared: Boolean = false
    /**
     * 标识已经触发过懒加载数据
     */
    protected var hasFetchData: Boolean = false

    protected lateinit var viewModel: VM

    private val _processBar: ProgressDialog by lazy { ProgressDialog(activity, ProgressDialog.THEME_HOLO_LIGHT) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = obtainViewModel(getVMClass())
        bindingVM()
        isViewPrepared = true
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
    private fun bindingVM() {
        binding.setVariable(BR.viewModel, viewModel)
    }

    /**
     * 获取ViewModel实例
     *
     * @param modelClass
     * @return
     */
    protected abstract fun obtainViewModel(modelClass: Class<VM>): VM

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyFetchDataIfPrepared()
            Timber.d("---------->setUserVisibleHint=>")
        }
    }

    private fun lazyFetchDataIfPrepared() {
        // 用户可见fragment && 没有加载过数据 && 视图已经准备完毕
        if (userVisibleHint && !hasFetchData && isViewPrepared) {
            hasFetchData = true
            lazyFetchData()
        }
    }

    /**
     * 懒加载的方式获取数据，仅在满足fragment可见和视图已经准备好的时候调用一次
     */
    protected abstract fun lazyFetchData()



    /**
     * 如果该页面不需要统一的loading效果可以重写此方法
     */
    protected open fun setLoadingBar() {
        viewModel.dataLoading.observe(this, EventObserver {
            if (it) showProcessBar(getLoadingText()) else closeDialog()
        })
    }

    protected open fun showProcessBar(message: String) {
        if (_processBar.isShowing()) {
            _processBar.dismiss()
        }

        if (TextUtils.isEmpty(message)) {
            val str = "正在获取信息，请稍候！"
            _processBar.setMessage(str)
        } else {
            _processBar.setMessage(message)
        }
        _processBar.show()
        _processBar.setCanceledOnTouchOutside(false)
        _processBar.setCancelable(true)
    }

    private fun closeDialog() {
        if (null != _processBar) {
            _processBar.cancel()
        }
    }

    /**
     * 重写此方法改变loading效果的文案
     *
     * @return
     */
    protected open fun getLoadingText(): String {
        return ""
    }

    /**
     * 加载错误统一提示，如果不需要就重写此方法
     */
    protected open fun setEorrorHint() {
        viewModel.dataLoadingError.observe(this, EventObserver {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
    }


}

