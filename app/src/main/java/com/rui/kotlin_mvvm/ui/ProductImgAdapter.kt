package com.rui.kotlin_mvvm.ui

import android.databinding.ObservableList
import android.support.v4.app.FragmentActivity
import android.support.v4.view.ViewPager

import com.rui.common.adapter.BaseRvAdapter
import com.rui.common.adapter.BaseRvViewHolder
import com.rui.kotlin_mvvm.R
import com.rui.kotlin_mvvm.databinding.ItemProductDtlBinding
import com.rui.kotlin_mvvm.model.ColorModel
import com.rui.mvvm.VPOnListChangedCallback
import com.rui.toolkit.DisplayUtils

import javax.inject.Inject

/**
 * Created by rui on 2019/6/25
 */
class ProductImgAdapter @Inject constructor() :
    BaseRvAdapter<ColorModel>(R.layout.item_product_dtl) {
    
    @Inject
    lateinit var activity: FragmentActivity

    override fun convert(helper: BaseRvViewHolder, item: ColorModel) {
        helper.addOnClickListener(R.id.item_camera)
            .addOnClickListener(R.id.rl_color)
            .addOnClickListener(R.id.item_camera_right)

        val binding = helper.getBinding() as ItemProductDtlBinding
        val itemImageAdapter = ImagePagerAdapter(activity, helper.adapterPosition, item)
        binding.adapter = itemImageAdapter
        val vpOnListChangedCallback = VPOnListChangedCallback<ObservableList<Any>>()
        vpOnListChangedCallback.adapter = itemImageAdapter
        item.localZSImgs.addOnListChangedCallback(vpOnListChangedCallback)
        binding.itemViewModel = item
        binding.executePendingBindings()

        val screenWidth = DisplayUtils.getScreenWidthAndHight(mContext.applicationContext)[0]
        val layoutParams = binding.itemPager.getLayoutParams()
        layoutParams.height = screenWidth
        binding.itemPager.setLayoutParams(layoutParams)
        val rlCameraParams = binding.rlCamera.getLayoutParams()
        rlCameraParams.height = screenWidth
        binding.rlCamera.setLayoutParams(rlCameraParams)

        binding.itemPager.currentItem = item.currentPosition.get()
        binding.itemPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                item.currentPosition.set(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

}
