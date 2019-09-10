package com.rui.toolkit

import android.content.Context
import android.widget.Toast

/**
 *Created by rui on 2019/8/8
 */
fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}