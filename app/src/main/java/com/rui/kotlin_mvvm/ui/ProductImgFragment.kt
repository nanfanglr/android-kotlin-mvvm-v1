package com.rui.kotlin_mvvm.ui

import android.content.Context
import android.databinding.ObservableList
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.rui.common.ConstantVal
import com.rui.common.adapter.BaseRvAdapter
import com.rui.common.base.BasePageVMFragment
import com.rui.kotlin_mvvm.R
import com.rui.kotlin_mvvm.databinding.FragmentProductImgBinding
import com.rui.kotlin_mvvm.di.vmodel.ProductImgFgVModel
import com.rui.kotlin_mvvm.model.ProductModel
import com.rui.mvvm.RvOnListChangedCallback
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import timber.log.Timber

/**
 *Created by rui on 2019/9/11
 */
class ProductImgFragment : BasePageVMFragment<
        FragmentProductImgBinding,
        ProductImgFgVModel,
        BaseRvAdapter<ProductModel>,
        LinearLayoutManager,
        RvOnListChangedCallback<ObservableList<Any>>
        >() {

    companion object {
        fun newInstance(context: Context, dataType: String, title: String): ProductImgFragment {
            val bundle = Bundle()
            bundle.putString("dataType", dataType)
            bundle.putString("title", title)
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
        if (viewModel.items.size <= 0)
            viewModel.getData(ConstantVal.LOAD_FIRST)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVM()
    }

    private fun initVM() {
        viewModel.dataType = arguments?.getString("dataType")
    }

}