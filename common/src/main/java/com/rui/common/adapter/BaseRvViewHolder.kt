package com.rui.common.adapter

import android.databinding.ViewDataBinding
import android.view.View
import com.chad.library.adapter.base.BaseViewHolder
import com.rui.common.R

/**
 *Created by rui on 2019/8/10
 */
class BaseRvViewHolder(view: View) : BaseViewHolder(view) {

    fun getBinding(): ViewDataBinding? {
        return itemView.getTag(R.id.BaseQuickAdapter_databinding_support) as ViewDataBinding
    }
}