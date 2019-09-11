package com.rui.kotlin_mvvm.di.vmodel

import com.rui.common.ConstantVal
import com.rui.common.base.BasePageVModel
import com.rui.kotlin_mvvm.di.repository.ProductRepository
import com.rui.kotlin_mvvm.model.ProductModel
import com.rui.mvvm.BaseApplication
import com.rui.retrofit2.basemodel.ResultModel
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject


/**
 *Created by rui on 2019/8/2
 */
class ProductImgFgVModel @Inject constructor(app: BaseApplication) :
    BasePageVModel<ProductModel>(app) {

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

    override fun getDataOB(): Single<ResultModel<ProductModel>> {
        Timber.d("-------->getDataOB")
        return repository.getProducts(dataType, searchKeyWord, page)
    }


    /**
     * 关键字搜索
     *
     * @param searchKeyWord
     */
    fun setSearchKeyWord(searchKeyWord: String) {
        this.searchKeyWord = searchKeyWord
        getData(ConstantVal.LOAD_FIRST)
    }

}