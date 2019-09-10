package com.rui.retrofit2.basemodel

import java.io.Serializable

/**
 * Created by rui on 2018/3/9.
 */
open class BaseModel : Serializable {
    /**
     * 这个属性用于子类在数组中的下标，
     * 在使用databinding绑定数据时，用来显示列表中的序号
     */
    var arrayIndex: Int = 0

//    fun getInt(value: Long?): Int {
//        return value?.toInt() ?: 0
//    }
//
//    fun getFloat(value: Double?): Float {
//        return value?.toFloat() ?: 0f
//    }
//
//    fun getString(value: String?): String {
//        var value = value
//        if (value == null)
//            value = ""
//        return value
//    }


}
