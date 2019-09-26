package com.rui.mvvm

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.view.WindowManager
import android.widget.Toast

/**
 *Created by rui on 2019/8/8
 */
fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}

/**
 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
 */
fun Context.dip2px(dpValue: Float): Int {
    return (dpValue * resources.displayMetrics.density + 0.5f).toInt()
}

/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
 */
fun Context.px2dip(pxValue: Float): Int {
    return (pxValue / resources.displayMetrics.density + 0.5f).toInt()
}

/**
 * 将px值转换为sp值，保证文字大小不变
 *
 * @param pxValue
 * @param pxValue
 * （DisplayMetrics类中属性scaledDensity）
 * @return
 */
fun Context.px2sp(pxValue: Float): Int {
    return (pxValue / resources.displayMetrics.scaledDensity + 0.5f).toInt()
}

/**
 * 将sp值转换为px值，保证文字大小不变
 *
 * @param spValue
 * @param spValue
 * （DisplayMetrics类中属性scaledDensity）
 * @return
 */
fun Context.sp2px(spValue: Float): Int {
    return (spValue * resources.displayMetrics.scaledDensity + 0.5f).toInt()
}

/**
 * 获取屏幕的宽
 */
fun Context.screenWith(): Int {
    val dm = resources.displayMetrics
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    wm.defaultDisplay.getMetrics(dm)
    return dm.widthPixels
}

/**
 * 获取屏幕的高度
 */
fun Context.screenHeight(): Int {
    val dm = resources.displayMetrics
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    wm.defaultDisplay.getMetrics(dm)
    return dm.heightPixels
}

/**
 * 获取颜色的扩展
 */
fun Context.color(@ColorRes res: Int): Int = ContextCompat.getColor(this, res)


/**
 * activity跳转的封装
 */
inline fun <reified T : Activity> Context.startActivity(vararg params: Pair<String, String>) {
    val intent = Intent(this, T::class.java)
    params.forEach { intent.putExtra(it.first, it.second) }
    startActivity(intent)
}
