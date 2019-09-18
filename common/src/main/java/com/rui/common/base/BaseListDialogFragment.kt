package com.rui.common.base

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.rui.mvvm.binding.RvOnListChangedCallback
import com.rui.mvvm.dialogfragment.BaseDaggerDialogFragment
import javax.inject.Inject
import javax.inject.Provider

/**
 *Created by rui on 2019/9/3
 */
abstract class BaseListDialogFragment<
        DB : ViewDataBinding,
        VM : BaseListVModel<*>,
        ADAPTER : BaseQuickAdapter<*, *>,
        LAYOUTMANAGER : RecyclerView.LayoutManager,
        RVCB : RvOnListChangedCallback<*>
        > : BaseDaggerDialogFragment<DB, VM>() {

    /**
     * 子类必须实现此方法，这样才能做列表的初始化
     *
     * @return
     */
    abstract val recyclerView: RecyclerView
    /**
     * 列表适配器
     */
    protected val adapter: ADAPTER by lazy {
        adapterProvider.get()
    }
    /**
     * 列表布局管理器
     */
    protected val layoutManager: LAYOUTMANAGER by lazy {
        layoutManagerProvider.get()
    }
    /**
     * 响应列表变化去刷新数据的回调
     */
    protected val rvOnListChangedCallback: RVCB by lazy {
        rvcbProvider.get()
    }

    @Inject
    protected lateinit var adapterProvider: Provider<ADAPTER>

    @Inject
    protected lateinit var layoutManagerProvider: Provider<LAYOUTMANAGER>

    @Inject
    protected lateinit var rvcbProvider: Provider<RVCB>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRV()
    }

    /**
     * 初始化列表相关的view及适配器
     */
    protected fun initRV() {
        recyclerView.let {
            adapter.setNewData(viewModel.items as List<Nothing>?)
            it.layoutManager = layoutManager
            it.adapter = adapter
//            binding.setVariable(BR.adapter, adapter)
//            binding.setVariable(BR.layoutManager, layoutManager)
            rvOnListChangedCallback.adapter = adapter
            viewModel.items.addOnListChangedCallback(rvOnListChangedCallback)
        }
    }

}