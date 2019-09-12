package com.rui.kotlin_mvvm.di.repository

import android.text.TextUtils
import com.luck.picture.lib.entity.LocalMedia
import com.rui.kotlin_mvvm.APPValue
import com.rui.kotlin_mvvm.model.ColorModel
import com.rui.kotlin_mvvm.model.ProductDtlModel
import com.rui.kotlin_mvvm.model.ProductModel
import com.rui.kotlin_mvvm.netservice.NetService
import com.rui.retrofit2.basemodel.ResultModel
import io.reactivex.Single
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 *Created by rui on 2019/9/11
 */
class ProductRepository @Inject constructor(private val netService: NetService) {
    /**
     * 获取商品列表数据
     *
     * @param dataType
     * @param keyWord
     */
    fun getProducts(
        dataType: String?,
        keyWord: String?,
        page: Int
    ): Single<ResultModel<ProductModel>> {
        return Single.create<ResultModel<ProductModel>> { emitter ->
            val resultModel = ResultModel<ProductModel>()
            resultModel.success = true
            val list = ArrayList<ProductModel>()
            for (i in 0..8) {
                val model = ProductModel()
                model.prod_NUM = "prod_NAME$page$i"
                model.prod_NAME = "女长裤(铅笔裤)$page$i"
                model.rack_RATE = (100 + i).toDouble()
                if (TextUtils.isEmpty(keyWord) && TextUtils.equals("T", dataType)) {
                    model.img_URL =
                        "https://ww1.sinaimg.cn/large/0065oQSqly1ftf1snjrjuj30se10r1kx.jpg"
                } else if (TextUtils.equals("T", dataType)) {
                    model.img_URL =
                        "https://ww1.sinaimg.cn/large/0065oQSqly1ftzsj15hgvj30sg15hkbw.jpg"
                }
                list.add(model)
            }
            resultModel.data = list
            resultModel.total = 29
            Timber.d("-------->getProducts.lise=${list.size}")
            emitter.onSuccess(resultModel)
        }
        //        return homeService.getProducts(dataType, TextUtils.isEmpty(keyWord) ? null : keyWord
        //                , page, ResultModel.PAGE_LIMIT);
    }

    fun getProdDtl(prodId: Int, prodNum: String): Single<ResultModel<ProductDtlModel>> {
        return Single.create<ResultModel<ProductDtlModel>> { emitter ->
            val resultModel = ResultModel<ProductDtlModel>()
            resultModel.success = true
            val model = ProductDtlModel()
            model.prod_NAME = "女长裤(铅笔裤)"
            model.prod_NUM = "B8VB1786"
            model.rack_RATE = 199.0

            val list0 = ArrayList<LocalMedia>()
            list0.add(
                getLocalMedia(
                    "https://ws1.sinaimg.cn/large/0065oQSqly1fuh5fsvlqcj30sg10onjk.jpg",
                    System.currentTimeMillis(), APPValue.NET_IMAGE
                )
            )
            list0.add(
                getLocalMedia(
                    "https://ws1.sinaimg.cn/large/0065oQSqly1fuo54a6p0uj30sg0zdqnf.jpg",
                    System.currentTimeMillis(), APPValue.NET_IMAGE
                )
            )
            list0.add(
                getLocalMedia(
                    "https://ww1.sinaimg.cn/large/0065oQSqly1fszxi9lmmzj30f00jdadv.jpg",
                    System.currentTimeMillis(), APPValue.NET_IMAGE
                )
            )
            model.imgsDT = list0

            val list = ArrayList<ColorModel>()
            for (i in 0..2) {
                val color = ColorModel()
                color.color_NAME = "紫色"
                color.clImgUrl.set("https://ww1.sinaimg.cn/large/0065oQSqly1fszxi9lmmzj30f00jdadv.jpg")
                color.localCLImgs?.add(
                    getLocalMedia(
                        "https://ww1.sinaimg.cn/large/0065oQSqly1fszxi9lmmzj30f00jdadv.jpg",
                        System.currentTimeMillis(), APPValue.NET_IMAGE
                    )
                )

                val list1 = ArrayList<LocalMedia>()
                list1.add(
                    getLocalMedia(
                        "https://ws1.sinaimg.cn/large/0065oQSqly1fuh5fsvlqcj30sg10onjk.jpg",
                        System.currentTimeMillis(), APPValue.NET_IMAGE
                    )
                )
                list1.add(
                    getLocalMedia(
                        "https://ws1.sinaimg.cn/large/0065oQSqly1fuo54a6p0uj30sg0zdqnf.jpg",
                        System.currentTimeMillis(), APPValue.NET_IMAGE
                    )
                )
                list1.add(
                    getLocalMedia(
                        "https://ww1.sinaimg.cn/large/0065oQSqly1fszxi9lmmzj30f00jdadv.jpg",
                        System.currentTimeMillis(), APPValue.NET_IMAGE
                    )
                )
                color.localZSImgs.addAll(list1)

                list.add(color)
            }
            model.colors = list
            resultModel.obj = model

            emitter.onSuccess(resultModel)
        }
        //        return homeService.getProductDtl(prodId > 0 ? prodId + "" : null, prodNum);
    }

    /**
     * 产生一个localMedia对象
     *
     * @param url
     * @param timeStamp
     * @param mimeType
     * @return
     */
    private fun getLocalMedia(url: String, timeStamp: Long, mimeType: Int): LocalMedia {
        val localMediaDT = LocalMedia()
        localMediaDT.cutPath = url
        localMediaDT.path = url
        localMediaDT.compressPath = url
        localMediaDT.duration = timeStamp //这个是用来表示图片是否已经更新的
        localMediaDT.mimeType = mimeType
        return localMediaDT
    }

////    public Single<ResultModel> saveProdDtlOB(long id, String imgs) {
////        return homeService.saveProductImgs(id, imgs);
////    }
//
//
//    fun getMultipleData(): Single<ResultModel<MultipleRvItemModel>> {
//        return Single.create<ResultModel<MultipleRvItemModel>> { emitter ->
//            val resultModel = ResultModel()
//            resultModel.setSuccess(true)
//            val pageData = ResultModel.PageData()
//            val list = ArrayList<MultipleRvItemModel>()
//
//            // https://ww1.sinaimg.cn/large/0065oQSqly1ftf1snjrjuj30se10r1kx.jpg
//            // https://ww1.sinaimg.cn/large/0065oQSqly1ftzsj15hgvj30sg15hkbw.jpg
//            // https://ws1.sinaimg.cn/large/0065oQSqly1fubd0blrbuj30ia0qp0yi.jpg
//            // https://ws1.sinaimg.cn/large/0065oQSqly1fuh5fsvlqcj30sg10onjk.jpg
//            // https://ws1.sinaimg.cn/large/0065oQSqly1fuo54a6p0uj30sg0zdqnf.jpg
//            // https://ww1.sinaimg.cn/large/0065oQSqly1fszxi9lmmzj30f00jdadv.jpg
//
//            for (i in 0..1) {
//                list.add(
//                    MultipleRvItemModel(
//                        MultipleRvItemModel.SINGLE_IMG,
//                        "https://ww1.sinaimg.cn/large/0065oQSqly1ftf1snjrjuj30se10r1kx.jpg"
//                    )
//                )
//                list.add(
//                    MultipleRvItemModel(
//                        MultipleRvItemModel.SINGLE_TEXT,
//                        "https://ww1.sinaimg.cn/large/0065oQSqly1ftzsj15hgvj30sg15hkbw.jpg"
//                    )
//                )
//                list.add(
//                    MultipleRvItemModel(
//                        MultipleRvItemModel.TEXT_IMG,
//                        "https://ws1.sinaimg.cn/large/0065oQSqly1fubd0blrbuj30ia0qp0yi.jpg"
//                    )
//                )
//                list.add(
//                    MultipleRvItemModel(
//                        MultipleRvItemModel.TEXT_IMG,
//                        "https://ws1.sinaimg.cn/large/0065oQSqly1fuh5fsvlqcj30sg10onjk.jpg"
//                    )
//                )
//                list.add(
//                    MultipleRvItemModel(
//                        MultipleRvItemModel.TEXT_IMG,
//                        "https://ww1.sinaimg.cn/large/0065oQSqly1fszxi9lmmzj30f00jdadv.jpg"
//                    )
//                )
//            }
//
//
//            pageData.setList(list)
//            resultModel.setPageData(pageData)
//            resultModel.setTotal(29)
//            emitter.onSuccess(resultModel)
//
//        }
//    }
}