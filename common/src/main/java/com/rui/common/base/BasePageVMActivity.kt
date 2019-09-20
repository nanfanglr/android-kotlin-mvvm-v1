package com.rui.common.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.rui.common.ConstantVal
import com.rui.common.R
import com.rui.common.databinding.EmptyViewVmBinding
import com.rui.mvvm.EventObserver
import com.rui.mvvm.activity.BaseDaggerActivity
import com.rui.mvvm.binding.RvOnListChangedCallback
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import javax.inject.Inject
import javax.inject.Provider

abstract class BasePageVMActivity<
        DB : ViewDataBinding,
        VM : BaseListVModel<*>,
        ADAPTER : BaseQuickAdapter<*, *>,
        LAYOUTMANAGER : RecyclerView.LayoutManager,
        RVCB : RvOnListChangedCallback<*>
        > : BaseDaggerActivity<DB, VM>() {
    /**
     * 子类必须实现此方法，这样才能做列表的初始化
     *
     * @return
     */
    abstract val recyclerView: RecyclerView
    /**
     * 子类必须实现此方法，这样才能做列表的初始化
     *
     * @return
     */
    abstract val refreshLayout: SmartRefreshLayout?
    /**
     * 空的布局
     */
    protected val emptyView: View by lazy {
        val emptyViewVmBinding = DataBindingUtil.inflate<EmptyViewVmBinding>(
            LayoutInflater.from(this),
            R.layout.empty_view_vm,
            recyclerView.parent as ViewGroup,
            false
        ).apply {
            this.viewModel = this@BasePageVMActivity.viewModel
        }
        emptyViewVmBinding.root
    }

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRefreshLayout()
        initRV()
        initEmptyOB()
    }

    /**
     * 初始化上来加载及下拉刷新ui
     */
    protected fun initRefreshLayout() {
        refreshLayout?.run {
            val classicsHeader = ClassicsHeader(this@BasePageVMActivity)
            classicsHeader.setTextSizeTitle(14f)
            classicsHeader.setTextSizeTime(10f)
            classicsHeader.setPrimaryColor(Color.parseColor("#eeeeee"))
            setRefreshHeader(classicsHeader)
            val classicsFooter = ClassicsFooter(this@BasePageVMActivity)
            classicsFooter.setTextSizeTitle(14f)
            classicsFooter.setPrimaryColor(Color.parseColor("#eeeeee"))
            setRefreshFooter(classicsFooter)
            setHeaderHeight(46f)
            setFooterHeight(46f)
            setOnRefreshListener { viewModel.getData(ConstantVal.LOAD_REFRESH) }
            setOnLoadMoreListener { viewModel.getData(ConstantVal.LOAD_MORE) }
        }
    }

    /**
     * 初始化列表相关的view及适配器
     */
    protected open fun initRV() {
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

    /**
     *
     */
    private fun initEmptyOB() {
        viewModel.empty.observe(this, EventObserver {
            viewModel.emptyText.set(this.getString(R.string.empty_no_data))
            setEmptyView()
        })
    }

    /**
     * 设置空布局显示的方法
     */
    private fun setEmptyView() {
        adapter.emptyView = emptyView
    }

    override fun setErrorHint() {
        viewModel.dataLoadingError.observe(this, EventObserver {
            if (adapter.itemCount > 0) {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            } else {
                viewModel.emptyText.set(it)
                setEmptyView()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        //这里必须去除改监听，否则因callback中的adapter对象持有activity的引用导致内存泄漏
        viewModel.items.removeOnListChangedCallback(rvOnListChangedCallback)
    }

}
