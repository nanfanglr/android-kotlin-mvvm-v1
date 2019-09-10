package com.rui.mvvm.activity

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity


/**
 *Created by rui on 2019/8/1
 */
open abstract class BaseDBActivity<DB : ViewDataBinding>
    : AppCompatActivity() {

    /**
     * 当前页面的ViewDataBinding类，由databinding自动生成
     */
    protected lateinit var binding: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutID(savedInstanceState))
    }

    /**
     * 初始化view
     *
     * @param savedInstanceState
     * @return
     */
    protected abstract fun getLayoutID(savedInstanceState: Bundle?): Int

}