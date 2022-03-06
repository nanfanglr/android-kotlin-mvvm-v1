package com.rui.kotlin_mvvm.ui.product_dtl

import androidx.databinding.ObservableList
import androidx.fragment.app.FragmentActivity
import com.rui.common.adapter.BaseRvAdapter
import com.rui.common.adapter.BaseRvViewHolder
import com.rui.kotlin_mvvm.R
import com.rui.kotlin_mvvm.databinding.ItemProductDtlBinding
import com.rui.kotlin_mvvm.model.ColorModel
import com.rui.mvvm.binding.VPOnListChangedCallback
import com.rui.mvvm.screenWith
import javax.inject.Inject

/**
 * Created by rui on 2019/6/25
 */
class ProductImgAdapter @Inject constructor() :
    BaseRvAdapter<ColorModel>(R.layout.item_product_dtl) {

    @Inject
    lateinit var activity: FragmentActivity

    override fun convert(helper: BaseRvViewHolder, item: ColorModel) {
        //列表item数据绑定
//        val binding = helper.getBinding() as? ItemProductDtlBinding
//        val itemImageAdapter =
//            ImagePagerAdapter(
//                activity,
//                helper.adapterPosition,
//                item
//            )
//        binding.adapter = itemImageAdapter
//        val vpOnListChangedCallback =
//            VPOnListChangedCallback<ObservableList<Any>>()
//        vpOnListChangedCallback.adapter = itemImageAdapter
//        item.localZSImgs.addOnListChangedCallback(vpOnListChangedCallback)
//        binding.itemViewModel = item
//        binding.executePendingBindings()
//
//        //列表item点击事件处理
//        helper.addOnClickListener(R.id.item_camera)
//            .addOnClickListener(R.id.rl_color)
//            .addOnClickListener(R.id.item_camera_right)
//
//        //列表item-viewpager宽度动态处理
//        binding.itemPager.layoutParams.height = mContext.screenWith()
//        binding.rlCamera.layoutParams.height = mContext.screenWith()
//
//        binding.itemPager.apply {
//            currentItem = item.currentPosition.get()
//            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//                override fun onPageScrolled(
//                    position: Int,
//                    positionOffset: Float,
//                    positionOffsetPixels: Int
//                ) {
//                }
//
//                override fun onPageSelected(position: Int) {
//                    item.currentPosition.set(position)
//                }
//
//                override fun onPageScrollStateChanged(state: Int) {}
//            })
//        }
    }

}
