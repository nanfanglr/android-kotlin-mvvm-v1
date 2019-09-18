package com.rui.retrofit2.basemodel

/**
 * Created by rui on 2018/3/9.
 */
open class BaseResultModel : BaseModel() {

    var url: String = ""

    var pageCount: Int = 0

    var relative_url: String = ""

    var success: Boolean = false

    var msg: String = "unkown msg"

    var rows: String = ""

    var total: Int = 0

    var error: Int = 0

    var error_code: Int = 0

    var page: Int = 0

    val sumPage: Int
        get() = getSumPage(PAGE_LIMIT)

    fun getSumPage(limitPage: Int): Int {
        return if (total % limitPage == 0) total / limitPage else total / limitPage + 1
    }

    companion object {
        /**
         * url : null
         * pageCount : 0
         * relative_url : null
         * success : true
         * msg : null
         * rows : null
         * total : 0
         * error : 0
         * page : 0
         */
        var PAGE_LIMIT = 10
    }

}
