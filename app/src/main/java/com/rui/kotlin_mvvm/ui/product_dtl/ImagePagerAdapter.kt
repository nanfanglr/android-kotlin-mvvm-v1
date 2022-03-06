package com.rui.kotlin_mvvm.ui.product_dtl

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.rui.common.ImageLoader
import com.rui.kotlin_mvvm.APPValue
import com.rui.kotlin_mvvm.model.ColorModel
import com.rui.kotlin_mvvm.ui.edit_images.EditImagesActivity
import javax.inject.Inject

/**
 * Created by rui on 2019/6/25
 */
class ImagePagerAdapter : PagerAdapter {
    @Inject
    lateinit var activity: FragmentActivity
    private var imgs: ArrayList<LocalMedia>
    private var rvItemPos = -1
    lateinit var colorModel: ColorModel
     var disableClick: Boolean = false

    @Inject
    constructor() {
        imgs = ArrayList()
    }

    constructor(activity: FragmentActivity, rvItemPos: Int, colorModel: ColorModel) {
        this.imgs = colorModel.localZSImgs
        this.rvItemPos = rvItemPos
        this.colorModel = colorModel
        this.activity = activity
    }

    fun setSelectList(selectList: List<LocalMedia>) {
        this.imgs = selectList as ArrayList<LocalMedia>
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return imgs.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val localMedia = imgs[position]
        val imageView = ImageView(container.context)
        if (!disableClick)
            imageView.setOnClickListener {
                EditImagesActivity.actionStart(
                    activity, imgs, position, rvItemPos
                    , if (rvItemPos == -1) APPValue.HEAD_REQUESTCODE else APPValue.ITEM_REQUESTCODE
                )
            }
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        val path = localMedia.compressPath
        val mineType = localMedia.mimeType
        if (mineType == PictureMimeType.ofImage()) {
            ImageLoader.displayImage(container.context, path, imageView)
        } else {
            ImageLoader.displayImage(
                container.context, path, imageView
                , localMedia.duration
            )
        }
        container.addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        container.removeView(any as View)
    }

    override fun isViewFromObject(view: View, any: Any): Boolean {
        return view === any
    }

    override fun getItemPosition(any: Any): Int {
        return POSITION_NONE
    }

}
