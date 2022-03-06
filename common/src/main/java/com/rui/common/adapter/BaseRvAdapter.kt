package com.rui.common.adapter

import androidx.annotation.LayoutRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.rui.retrofit2.basemodel.BaseModel

/**
 *Created by rui on 2019/8/10
 * 不能满足需求时，继承此基类，重写相关方法，实现需要的业务
 */
open class BaseRvAdapter<T : BaseModel> constructor(@LayoutRes layoutResId: Int) :
    BaseQuickAdapter<T, BaseRvViewHolder>(layoutResId) {
    //    /**
//     * 点击事件的viewId集合
//     */
//    private val clickIds = ArrayList<Int>()
//
//    override fun getItemView(layoutResId: Int, parent: ViewGroup): View {
//        val binding =
//            DataBindingUtil.inflate<ViewDataBinding>(mLayoutInflater, layoutResId, parent, false)
//                ?: return super.getItemView(layoutResId, parent)
//        return binding.root.apply { setTag(R.id.BaseQuickAdapter_databinding_support, binding) }
//    }
//
//    override fun convert(helper: BaseRvViewHolder, item: T) {
//        item.arrayIndex = helper.layoutPosition
//        helper.getBinding()?.apply {
//            setVariable(BR.itemViewModel, item)
//            executePendingBindings()
//        }
//        clickIds.forEach { helper.addOnClickListener(it) }
//    }
//
//    /**
//     * 添加点击事件
//     *
//     * @param ids 需要添加点击事件的view的id
//     */
//    fun addItemChildClickListener(@IdRes vararg ids: Int) {
//        ids.forEach { clickIds.add(it) }
//    }
    override fun convert(holder: BaseRvViewHolder, item: T) {

    }
}