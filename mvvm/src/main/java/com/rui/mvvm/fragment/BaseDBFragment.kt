package com.rui.mvvm.fragment


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment


/**
 *Created by rui on 2019/8/1
 */
open abstract class BaseDBFragment<DB : ViewDataBinding> : Fragment() {

    protected lateinit var binding: DB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutID(savedInstanceState), container, false)
        return binding.root
    }

    /**
     * 初始化view
     *
     * @param savedInstanceState
     * @return
     */
    protected abstract fun getLayoutID(savedInstanceState: Bundle?): Int


}