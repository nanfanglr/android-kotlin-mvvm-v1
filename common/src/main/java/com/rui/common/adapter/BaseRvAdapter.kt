package com.rui.common.adapter

import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

open class BaseRvAdapter<ITEM> constructor(@LayoutRes layoutResId: Int) :
    BaseQuickAdapter<ITEM, BaseDataBindingHolder<ViewDataBinding>>(layoutResId) {
    /**
     *点击事件的viewId集合
     */
    private val clickIds = ArrayList<Int>()

    override fun convert(holder: BaseDataBindingHolder<ViewDataBinding>, item: ITEM) {
        holder.dataBinding?.apply {
            setVariable(BR.itemViewModel, item)
            executePendingBindings()
        }
        clickIds.forEach { addChildClickViewIds(it) }
    }

    /**
     * 添加点击事件
     *
     * @param ids 需要添加点击事件的view的id
     */
    fun addItemChildClickListener(@IdRes vararg ids: Int) {
        ids.forEach { clickIds.add(it) }
    }
}