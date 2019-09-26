package com.rui.common

/**
 * Created by linet on 2017/8/22.
 */

import android.content.Context
import android.support.annotation.DrawableRes
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey


/**
 * Class works as our image loading library wrapper.
 */
object ImageLoader {

    /**
     * 默认加载显示RequestOptions
     * 默认的loading、error、null图片
     *
     * @return
     */
    private val defualtOption: RequestOptions
        get() = getOption(
            R.drawable.img_loading,
            R.drawable.error,
            R.drawable.default_img,
            0.toString()
        )

    /**
     * 默认加载图片的方法
     *
     * @param context
     * @param url
     * @param imageView
     */
    fun displayImage(context: Context, url: String?, imageView: ImageView) {
        displayImageBase(context, url, imageView, defualtOption)
    }

    /**
     * 自定义loading、error、null图片的RequestOptions
     *
     * @param loadingDrawable
     * @return
     */
    private fun getOption(
        @DrawableRes loadingDrawable: Int, @DrawableRes errorDrawable: Int = R.drawable.error, @DrawableRes nullDrawable: Int = R.drawable.img_loading,
        timeStamp: String = 0.toString() + ""
    ): RequestOptions {
        //        DiskCacheStrategy.ALL 使用DATA和RESOURCE缓存远程数据，仅使用RESOURCE来缓存本地数据。
        //        DiskCacheStrategy.NONE 不使用磁盘缓存
        //        DiskCacheStrategy.DATA 在资源解码前就将原始数据写入磁盘缓存
        //        DiskCacheStrategy.RESOURCE 在资源解码后将数据写入磁盘缓存，即经过缩放等转换后的图片资源。
        //        DiskCacheStrategy.AUTOMATIC 根据原始图片数据和资源编码策略来自动选择磁盘缓存策略。
        return RequestOptions()
            //                .centerCrop()
            .placeholder(loadingDrawable)//加载中默认图片
            .error(errorDrawable)//  加载错误默认图片
            .fallback(nullDrawable)//请求url/model为空
            .skipMemoryCache(false)
            .signature(ObjectKey(timeStamp))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
    }

    private fun displayImageBase(
        context: Context, url: String?, imageView: ImageView, options: RequestOptions
    ) {
        try {
            Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 默认加载图片的方法
     *
     * @param context
     * @param url
     * @param imageView
     */
    fun displayImage(context: Context, url: String?, imageView: ImageView, timeStamp: Long) {
        displayImageBase(
            context, url, imageView,
            getOption(
                R.drawable.img_loading,
                R.drawable.error,
                R.drawable.default_img,
                timeStamp.toString() + ""
            )
        )

    }

    /**
     * 默认加载图片的方法
     *
     * @param context
     * @param url
     * @param imageView
     * @param timeStamp 这个使用来表示是否更新图片的标识
     */
    fun displayImage(context: Context, url: String, imageView: ImageView, timeStamp: String) {
        displayImageBase(
            context, url, imageView,
            getOption(R.drawable.img_loading, R.drawable.error, R.drawable.default_img, timeStamp)
        )
    }

    /**
     * 加载头像的方法，有默认的头像
     *
     * @param context
     * @param uri
     * @param imageView
     */
    fun displayHeadImage(context: Context, uri: String, imageView: ImageView) {
        displayImage(
            context,
            uri,
            imageView,
            R.drawable.icon_default_head,
            R.drawable.icon_default_head,
            R.drawable.icon_default_head
        )
    }

    /**
     * 自定义loading、error、null图片的加载方法
     *
     * @param context
     * @param url
     * @param imageView
     * @param loadingDrawable
     */
    fun displayImage(
        context: Context,
        url: String,
        imageView: ImageView, @DrawableRes loadingDrawable: Int, @DrawableRes errorDrawable: Int, @DrawableRes nullDrawable: Int
    ) {
        displayImageBase(
            context,
            url,
            imageView,
            getOption(loadingDrawable, errorDrawable, nullDrawable, 0.toString() + "")
        )
    }

    /**
     * 自定义loading图片的加载方法
     *
     * @param context
     * @param url
     * @param imageView
     * @param loadingDrawable
     */
    fun displayImage(
        context: Context, url: String, imageView: ImageView, @DrawableRes loadingDrawable: Int
    ) {
        displayImageBase(context, url, imageView, getOption(loadingDrawable))

    }


}
