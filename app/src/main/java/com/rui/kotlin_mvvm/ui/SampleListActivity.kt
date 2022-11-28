package com.rui.kotlin_mvvm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import com.rui.kotlin_mvvm.R
import com.rui.kotlin_mvvm.databinding.ActivitySampleListBinding
import com.rui.kotlin_mvvm.ui.htmltext.HtmlTextActivity
import com.rui.kotlin_mvvm.ui.login.LoginActivity
import com.rui.mvvm.toast

/**
 *汇总页面，不需要数据源。
 */
class SampleListActivity : AppCompatActivity() {

    lateinit var contentViewBinding: ActivitySampleListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contentViewBinding = setContentView(this, R.layout.activity_sample_list)

        contentViewBinding.button.setOnClickListener {
            //login demo
            startActivity(Intent(this, LoginActivity::class.java))
        }

        contentViewBinding.button2.setOnClickListener {
            //single type list demo
            toast("2")
        }

        contentViewBinding.button3.setOnClickListener {
            //multiple type list demo
            toast("3")
        }

        contentViewBinding.button4.setOnClickListener {
            //drag list item demo
            toast("4")
        }

        contentViewBinding.button5.setOnClickListener {
            //single activity and multiple Fragment implements
            //use Navigation lib or not?
            toast("5")
        }

        contentViewBinding.button6.setOnClickListener {
            startActivity(Intent(this, HtmlTextActivity::class.java))
        }
    }
}