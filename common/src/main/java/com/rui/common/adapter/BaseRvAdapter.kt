package com.rui.common.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.view.View
import android.view.ViewGroup
import com.android.databinding.library.baseAdapters.BR
import com.chad.library.adapter.base.BaseQuickAdapter
import com.rui.common.R
import com.rui.retrofit2.basemodel.BaseModel

/**
 *Created by rui on 2019/8/10
 * 不能满足需求时，继承此基类，重写相关方法，实现需要的业务
 */
open class BaseRvAdapter<T : BaseModel> constructor(@LayoutRes layoutResId: Int) :
    BaseQuickAdapter<T, BaseRvViewHolder>(layoutResId) {

    override fun getItemView(layoutResId: Int, parent: ViewGroup): View {
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(mLayoutInflater, layoutResId, parent, false)
                ?: return super.getItemView(layoutResId, parent)
        val view = binding.root
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding)
        return view
    }

    override fun convert(helper: BaseRvViewHolder, item: T) {
        item.arrayIndex = helper.layoutPosition
        val binding = helper.getBinding()
        binding.setVariable(BR.itemViewModel, item)
        binding.executePendingBindings()
    }
}