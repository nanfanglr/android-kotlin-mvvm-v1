package com.rui.kotlin_mvvm.ui

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.rui.kotlin_mvvm.R
import com.rui.kotlin_mvvm.databinding.ActivityMainBinding
import com.rui.kotlin_mvvm.di.vmodel.MainVModel
import com.rui.kotlin_mvvm.ui.adapter.FgPagerAdapter
import com.rui.mvvm.EventObserver
import com.rui.mvvm.activity.BaseDaggerActivity
import javax.inject.Inject

class MainActivity : BaseDaggerActivity<ActivityMainBinding, MainVModel>() {

    override fun getVMClass(): Class<MainVModel> = MainVModel::class.java

    override fun getLayoutID(savedInstanceState: Bundle?): Int = R.layout.activity_main

    @Inject
    lateinit var fgPagerAdapter: FgPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initVM()
        initOB()
    }

    private fun initView() {
        binding.tabLayout.setupWithViewPager(binding.vpContainer)
    }

    private fun initVM() {
        fgPagerAdapter.fragmentList = viewModel.items
        binding.fgPagerAdapter = fgPagerAdapter
        viewModel.addPage()
    }

    override fun onDestroy() {
        viewModel.items.clear()
        super.onDestroy()
    }


    protected fun initOB() {
        viewModel.finishAct.observe(this, EventObserver { finish() })
        viewModel.closeKeyBoard.observe(this, EventObserver {
            if (currentFocus != null && currentFocus.windowToken != null) {
                (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .hideSoftInputFromWindow(
                        currentFocus.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS
                    )
            }
        })
    }

}
