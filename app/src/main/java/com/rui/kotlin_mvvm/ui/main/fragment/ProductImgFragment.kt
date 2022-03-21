package com.rui.kotlin_mvvm.ui.main.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rui.common.ConstantVal
import com.rui.common.adapter.BaseRvAdapter
import com.rui.common.base.BasePageVMFragment
import com.rui.kotlin_mvvm.R
import com.rui.kotlin_mvvm.databinding.FragmentProductImgBinding
import com.rui.kotlin_mvvm.ui.main.activity.MainVModel
import com.rui.kotlin_mvvm.model.ProductModel
import com.rui.kotlin_mvvm.ui.product_dtl.ProductDtlActivity
import com.rui.mvvm.toast
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import io.reactivex.disposables.Disposable
import timber.log.Timber

/**
 *Created by rui on 2019/9/11
 */
class ProductImgFragment : BasePageVMFragment<
        ProductModel,
        FragmentProductImgBinding,
        ProductImgFgVModel,
        BaseRvAdapter<ProductModel>,
        LinearLayoutManager
        >() {

    companion object {
        fun newInstance(context: Context, dataType: String, title: String): ProductImgFragment {
            val bundle = Bundle().apply {
                putString("dataType", dataType)
                putString("title", title)
            }

            return instantiate(
                context,
                ProductImgFragment::class.java.name,
                bundle
            ) as ProductImgFragment
        }
    }

    override val recyclerView: RecyclerView
        get() = binding.rvData
    override val refreshLayout: SmartRefreshLayout?
        get() = binding.refresh

    override fun getVMClass(): Class<ProductImgFgVModel> {
        return ProductImgFgVModel::class.java
    }

    override fun getLayoutID(savedInstanceState: Bundle?): Int = R.layout.fragment_product_img

    override fun lazyFetchData() {
        // 当没有数据时才可以去请求数据
        Timber.d("-------->lazyFetchData")
        if (viewModel.items1.value==null || viewModel.items1.value?.isEmpty() == true){
            Timber.d("-------->lazyFetchData2")
            viewModel.getData(ConstantVal.LOAD_FIRST)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVM()
        initOB()
        initRVClick()
    }

    private fun initVM() {
        viewModel.dataType = arguments?.getString("dataType")
    }

    private lateinit var subscribe: Disposable

    /**
     * fragment所在activity的的viewmodel
     */
    private lateinit var mainViewModel: MainVModel

    private fun initOB() {
        mainViewModel =
            ViewModelProviders.of(activity!!, viewModelFactory).get(MainVModel::class.java)
        subscribe = mainViewModel.subject.subscribe { s ->
            //收到搜索事件
            viewModel.setSearchKeyWord(s)
        }
    }

    private fun initRVClick() {
        adapter.setOnItemClickListener { adapter, _, position ->
            val clickItem = adapter.data[position] as ProductModel
            context?.toast("点击了$position")
            ProductDtlActivity.actionStart(activity, clickItem.prod_ID, null)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        subscribe.dispose()
    }

}