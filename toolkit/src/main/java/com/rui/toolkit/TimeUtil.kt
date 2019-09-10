package com.rui.toolkit

import java.text.SimpleDateFormat
import java.util.*

/**
 *Created by rui on 2019/8/28
 */
val ymd = SimpleDateFormat("yy-MM-dd")

fun ymdDate(paramDate: Long): String {
    var retDate: String
    try {
        retDate = ymd.format(Date(paramDate))
    } catch (e: Exception) {
        retDate = "1970-01-01"
    }
    return retDate
}
