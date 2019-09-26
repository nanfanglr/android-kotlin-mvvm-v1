package com.rui.mvvm

import android.os.Build
import java.text.DateFormat
import java.util.*

/**
 *Created by rui on 2019/9/26
 */
/**
 * 检查版本，然后如果满足条件则去执行。登录检查也可以这么写
 */
inline fun supportsLollipop(code: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        code()
    }
}

/**
 * 时间转换为字符串
 */
fun Long.toDateString(dateFormat: Int = DateFormat.MEDIUM): String {
    val df = DateFormat.getDateInstance(
        dateFormat, Locale.getDefault()
    )
    return df.format(this)
}