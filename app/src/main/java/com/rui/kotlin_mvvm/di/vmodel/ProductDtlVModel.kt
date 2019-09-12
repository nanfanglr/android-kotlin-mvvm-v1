package com.rui.kotlin_mvvm.di.vmodel

import android.databinding.*
import com.luck.picture.lib.entity.LocalMedia
import com.rui.common.base.BaseListVModel
import com.rui.kotlin_mvvm.di.repository.ProductRepository
import com.rui.kotlin_mvvm.model.ColorModel
import com.rui.mvvm.BaseApplication
import com.rui.mvvm.Event
import com.rui.mvvm.network.ApiErro.ApiErrorHelper
import com.rui.retrofit2.basemodel.ResultModel
import com.rui.toolkit.toast
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject


/**
 *Created by rui on 2019/8/2
 */
class ProductDtlVModel @Inject constructor(app: BaseApplication) :
    BaseListVModel<ColorModel>(app) {
    @Inject
    lateinit var rvClickPos: ObservableInt
    @Inject
    lateinit var headCurrentPos: ObservableInt
    @Inject
    lateinit var headImgs: ObservableList<LocalMedia>
    @Inject
    lateinit var productNum: ObservableField<String>
    @Inject
    lateinit var isShowCommit: ObservableBoolean
    /**
     * 商品id
     */
    var prodId: Int = 0
    @Inject
    lateinit var prodName: ObservableField<String>
    @Inject
    lateinit var prodPrice: ObservableDouble

    @Inject
    lateinit var repository: ProductRepository
    /**
     * 数据加载的类型
     */
    var dataType: String? = null
    /**
     * 搜索关键字
     */
    private var searchKeyWord: String? = null

    override fun getDataOB(): Single<ResultModel<ColorModel>> {
        return Single.never()
    }

    override fun getData(loadRefresh: Int) {
        Timber.d("------------>getData=")
        addSubscribe(
            repository.getProdDtl(prodId, productNum.get() ?: "")
                .compose(singleTransformer(loadRefresh))
                .subscribe(
                    {
                        Timber.d("------------>it.success=${it.success}")
                        if (it.success) {
                            val obj = it.obj
                            if (obj != null) {
                                this.headImgs.addAll(obj.imgsDT)
                                this.items.addAll(obj.colors)
                                this.prodName.set(obj.prod_NAME)
                                this.productNum.set(obj.prod_NUM)
                                this.prodPrice.set(obj.rack_RATE)
                            } else {
                                empty.value = Event(Unit)
                            }
                        } else {
                            dataLoadingError.value = Event(it.msg)
                        }
                    },
                    {
                        ApiErrorHelper.handleCommonError(app, it)
                    })
        )
    }

    fun saveProductImg() {
        app.toast("上传图片")
    }

}