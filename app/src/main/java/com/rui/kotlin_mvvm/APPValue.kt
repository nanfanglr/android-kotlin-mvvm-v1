package com.rui.kotlin_mvvm

//package com.mvp.rui.androidmvpdemo.common.constants;

/**
 * 这里主要定义APP用的常量
 * Created by rui on 2018/3/9.
 */
interface APPValue {
    companion object {

        val HTTP = "http://"
        val HTTPS = "https://"
        val FILE = "file://"

        /**
         * 第一次加载数据
         */
        val LOAD_FIRST = 0
        /**
         * 下拉刷新数据
         */
        val LOAD_REFRESH = 1
        /**
         * 上拉加载更多
         */
        val LOAD_MORE = 2

        //    int PAGE_LIMIT =10;

        val SP_PHONE = "sp_phone"

        /**
         * 在列表适配器中，分辨本地视频还是网络视频时使用
         */
        val NET_IMAGE = 31

        val HEAD_REQUESTCODE = 400

        val ITEM_REQUESTCODE = 403

        val RESULTCODE_HEAD_TAKEPHOTO = 105

        val RESULTCODE_ITEM_TAKEPHOTO = 107

        val RESULTCODE_COLOR_TAKEPHOTO = 111

        val RESULTCODE_DT_TAKEPHOTO = 112
        /**
         * 可选的最大图片数量
         */
        val MAX_IMG_NUM = 10
    }

}
