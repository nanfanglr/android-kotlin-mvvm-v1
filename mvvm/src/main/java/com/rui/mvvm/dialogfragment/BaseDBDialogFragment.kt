package com.rui.mvvm.dialogfragment

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager


/**
 *Created by rui on 2019/8/1
 */
open abstract class BaseDBDialogFragment<DB : ViewDataBinding> : DialogFragment() {

    protected lateinit var binding: DB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, getLayoutID(savedInstanceState), container, false)
        return binding.root
    }

    /**
     * 初始化view
     *
     * @param savedInstanceState
     * @return
     */
    protected abstract fun getLayoutID(savedInstanceState: Bundle?): Int


    /**
     * 获取屏幕的像素
     *
     * @param context
     * @return
     */
    protected fun getScreenWidthAndHight(context: Context?): IntArray {
        val wh = IntArray(2)

        val dm = context?.resources?.displayMetrics
        val wm = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(dm)

        wh[0] = dm?.widthPixels ?: 0
        wh[1] = dm?.heightPixels ?: 0
        return wh
    }


}