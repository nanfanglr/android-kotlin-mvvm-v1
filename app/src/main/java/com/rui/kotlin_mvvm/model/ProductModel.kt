package com.rui.kotlin_mvvm.model

import com.rui.retrofit2.basemodel.BaseModel
/**
 * Created by rui on 2019/9/11
 */
class ProductModel : BaseModel() {
    /**
     * img_URL : /b8vb1786/b8vb1786_1.jpg
     * position : C
     * position_NAME : null
     * prod_ID : 1
     * prod_NAME : 女长裤(铅笔裤)
     * rack_RATE : 189.0
     * prod_NUM : B8VB1786
     * cat_ID : 62
     * cat_NAME : null
     * status_NAME : null
     * market_LVL : 1
     * brand_NUM : B
     * year_VAL : 2018
     * season_NUM : 4
     * gender_NUM : F
     * market_LVL_NAME : null
     * brand_NUM_NAME : null
     * season_NUM_NAME : null
     * create_TIME : 1536746828000
     * status : A
     * backup : null
     * loaded : true
     */

    /**
     * 商品图片
     */
    var img_URL: String? = null
        get() = if (field == null) "" else field
    var position: String? = null
        get() = if (field == null) "" else field
    var position_NAME: String? = null
        get() = if (field == null) "" else field
    /**
     * 商品id
     */
    var prod_ID: Int = 0
    /**
     * 商品名称
     */
    var prod_NAME: String? = null
        get() = if (field == null) "" else field
    /**
     * 价格
     */
    var rack_RATE: Double = 0.toDouble()
    /**
     * 商品款号
     */
    var prod_NUM: String? = null
        get() = if (field == null) "" else field
    var cat_ID: Int = 0
    var cat_NAME: String? = null
        get() = if (field == null) "" else field
    var status_NAME: String? = null
        get() = if (field == null) "" else field
    var market_LVL: String? = null
        get() = if (field == null) "" else field
    var brand_NUM: String? = null
        get() = if (field == null) "" else field
    var year_VAL: String? = null
        get() = if (field == null) "" else field
    var season_NUM: String? = null
        get() = if (field == null) "" else field
    var gender_NUM: String? = null
        get() = if (field == null) "" else field
    var market_LVL_NAME: String? = null
        get() = if (field == null) "" else field
    var brand_NUM_NAME: String? = null
        get() = if (field == null) "" else field
    var season_NUM_NAME: String? = null
        get() = if (field == null) "" else field
    var create_TIME: Long = 0
    var status: String? = null
        get() = if (field == null) "" else field
    var backup: String? = null
        get() = if (field == null) "" else field
    var isLoaded: Boolean = false

    /**
     * 仅是一个标记,用于更新图片缓存
     */
    var loadTime = System.currentTimeMillis()
}
