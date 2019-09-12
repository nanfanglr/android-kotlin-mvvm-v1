package com.rui.kotlin_mvvm.ui

import android.support.v4.app.FragmentActivity
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.rui.common.ImageLoader
import com.rui.kotlin_mvvm.model.ColorModel
import java.util.*
import javax.inject.Inject

/**
 * Created by rui on 2019/6/25
 */
class ImagePagerAdapter : PagerAdapter {
    @Inject
    lateinit var activity: FragmentActivity
    private var imgs: ArrayList<LocalMedia>? = null
    private var rvItemPos = -1
    lateinit var colorModel: ColorModel
    private var disableClick: Boolean = false

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
        return if (imgs == null) 0 else imgs!!.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val localMedia = imgs?.get(position)
        val imageView = ImageView(container.context)
        if (!disableClick)
            imageView.setOnClickListener { v ->
                //            EditImagesActivity.actionStart(activity, imgs, position, rvItemPos
                //                    , rvItemPos == -1 ? APPValue.HEAD_REQUESTCODE : APPValue.ITEM_REQUESTCODE);
            }
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        val path = localMedia?.compressPath
        val mineType = localMedia?.mimeType
        if (mineType == PictureMimeType.ofImage()) {
            ImageLoader.displayImage(container.getContext(), path, imageView);
        } else {
            ImageLoader.displayImage(
                container.getContext(), path, imageView
                , localMedia?.getDuration() ?: 0
            );
        }
        container.addView(imageView)
        return imageView
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    fun setDisableClick(enableClick: Boolean) {
        this.disableClick = enableClick
    }
}
