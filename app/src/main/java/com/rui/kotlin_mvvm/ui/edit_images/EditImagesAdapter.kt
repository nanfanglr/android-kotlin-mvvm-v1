package com.rui.kotlin_mvvm.ui.edit_images

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.rui.common.ImageLoader
import com.rui.common.adapter.BaseRvAdapter
import com.rui.kotlin_mvvm.APPValue
import com.rui.kotlin_mvvm.R
import com.rui.mvvm.dip2px
import com.rui.mvvm.screenWith
import com.rui.retrofit2.basemodel.BaseModel
import java.util.*
import javax.inject.Inject

/**
 * 这个适配器由于使用第三方LocalMedia数据类（不可以修改类属性），因此就不用databinding对他进行绑定
 * 如果需要使用databinding，需要继承LocalMedia这个类去使用绑定
 */
class EditImagesAdapter @Inject constructor() :
    BaseRvAdapter<BaseModel>(R.layout.item_samll_image) {

//    private var currentPosition: Int = 0
//
//    fun setCurrentPosition(currentPosition: Int) {
//        this.currentPosition = currentPosition
//        notifyDataSetChanged()
//    }
//
//    override fun convert(helper: BaseViewHolder, item: LocalMedia) {
//        helper.addOnClickListener(R.id.iv_small)
//        val mineType = item.mimeType
//
//        val iv = helper.getView<ImageView>(R.id.iv_small)
//        iv.scaleType = ImageView.ScaleType.CENTER_CROP
//
//        setImageViewSize(
//            mContext,
//            iv
//        )
//
//        if (mineType == PictureMimeType.ofImage()) {
//            //本地图片的展示
//            ImageLoader.displayImage(mContext, item.compressPath, iv)
//        } else if (mineType == APPValue.NET_IMAGE) {
//            //网络图片的展示
//            ImageLoader.displayImage(
//                mContext,
//                item.path,
//                iv,
//                item.duration
//            )
//        }
//
//        //默认当前展示的图片为选中状态
//        iv.isSelected = helper.adapterPosition == currentPosition
//
//    }
//
//    override fun getViewByPosition(position: Int, viewId: Int): View? {
//        return super.getViewByPosition(position, viewId)
//    }
//
//    companion object {
//        /**
//         * 设置图片的长宽
//         */
//        fun setImageViewSize(context: Context, imageView: View) {
//            val imageWidth = (context.screenWith()
//                    - context.dip2px(10f) * 3 - context.dip2px(16f) * 2) / 4
//            imageView.layoutParams.height = imageWidth
//            imageView.layoutParams.width = imageWidth
//        }
//    }

}

