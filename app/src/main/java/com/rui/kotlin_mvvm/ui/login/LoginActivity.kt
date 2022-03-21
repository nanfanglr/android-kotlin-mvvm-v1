package com.rui.kotlin_mvvm.ui.login

import android.content.Intent
import android.os.Bundle
import com.rui.kotlin_mvvm.R
import com.rui.kotlin_mvvm.databinding.ActivityLoginBinding
import com.rui.kotlin_mvvm.ui.main.activity.MainActivity
import com.rui.mvvm.EventObserver
import com.rui.mvvm.activity.BaseDaggerActivity


class LoginActivity : BaseDaggerActivity<ActivityLoginBinding, LoginVModel>() {

    override fun getVMClass(): Class<LoginVModel> = LoginVModel::class.java

    override fun getLayoutID(savedInstanceState: Bundle?): Int =
        R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initOB()
    }

    private fun initOB() {
        viewModel.initPhone()
        viewModel.loginSuccess.observe(this, EventObserver {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })
    }
}


