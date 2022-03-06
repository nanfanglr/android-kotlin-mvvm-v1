package com.rui.kotlin_mvvm.ui.login


import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.rui.kotlin_mvvm.repository.UserRepository
import com.rui.mvvm.BaseApplication
import com.rui.mvvm.Event
import com.rui.mvvm.network.ApiErro.ApiErrorHelper
import com.rui.mvvm.toast
import com.rui.mvvm.vmodel.BaseViewModel
import com.rui.toolkit.Preference
import javax.inject.Inject


/**
 *Created by rui on 2019/8/2
 */
class LoginVModel @Inject constructor(app: BaseApplication) : BaseViewModel(app) {
    /**
     * 登录是否成功的事件
     */
    @Inject
    lateinit var loginSuccess: MutableLiveData<Event<Unit>>
    /**
     * edittext输入值
     */
    @Inject
    lateinit var phone: ObservableField<String>
    /**
     *保存本地的sharePreference
     */
    var userMobile by Preference(app, "userMobile", "")
    /**
     * 登录密码
     */
    @Inject
    lateinit var psw: ObservableField<String>
    /**
     * 将edittext光标的位置移动到最后
     */
    @Inject
    lateinit var phoneLength: ObservableInt

    @Inject
    lateinit var repository: UserRepository

    fun initEvent() {
        phone.set(userMobile)
        phone.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                //需要判断inputPhone是否为空 否则会出现输入第一个数无法选择到末尾的情况
                phone.get()?.run {
                    phoneLength.set(this.length)
                }
            }
        })
    }

    fun onClickListenerBinding(view: View) {
        when {
            phone.get().isNullOrEmpty() -> {
                app.toast("请填写手机号码")
                return
            }
            psw.get().isNullOrEmpty() -> {
                app.toast("请输入登录密码")
                return
            }
            else -> {
                val imm = app.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
        addSubscribe(repository
            .getLoginOB(phone.get(), psw.get())
            .compose(singleTransformer())
            .subscribe(
                {
                    if (it.success) {
                        loginSuccess.value = Event(Unit)
                        userMobile = phone.get().toString()
                        app.toast("登录成功${phone.get().toString()}")
                    } else {
                        app.toast(it.msg)
                    }
                },
                //这里还可以用exfuc()来
                //exfuc()
                {
                    ApiErrorHelper.handleCommonError(app, it)
                }
            )
        )
    }

    fun exfuc(): (t: Throwable) -> Unit {
        return {
            ApiErrorHelper.handleCommonError(app, it)
        }
    }

}