package com.rui.common.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.rui.mvvm.dialogfragment.BaseDaggerDialogFragment
import javax.inject.Inject
import javax.inject.Provider

import androidx.databinding.library.baseAdapters.BR

/**
 *Created by rui on 2019/9/3
 */
abstract class BaseListDialogFragment<
        ITEM,
        DB : ViewDataBinding,
        VM : BaseListVModel<ITEM>,
        ADAPTER : BaseQuickAdapter<ITEM, *>,
        LAYOUTMANAGER : RecyclerView.LayoutManager
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

    @Inject
    protected lateinit var adapterProvider: Provider<ADAPTER>

    @Inject
    protected lateinit var layoutManagerProvider: Provider<LAYOUTMANAGER>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRV()
    }

    /**
     * 初始化列表相关的view及适配器
     */
    protected fun initRV() {
        recyclerView.let {
            it.layoutManager = layoutManager
            it.adapter = adapter

            binding.setVariable(BR.adapter, adapter)
            binding.setVariable(BR.layoutManager, layoutManager)

            viewModel.items.observe(this@BaseListDialogFragment.viewLifecycleOwner) {
                adapter.setList(it)
            }
        }
    }

}