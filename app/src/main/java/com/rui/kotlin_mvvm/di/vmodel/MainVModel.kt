package com.rui.kotlin_mvvm.di.vmodel

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import android.databinding.ObservableList
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.View
import com.rui.kotlin_mvvm.ui.ProductImgFragment
import com.rui.mvvm.BaseApplication
import com.rui.mvvm.Event
import com.rui.mvvm.vmodel.BaseViewModel
import com.rui.mvvm.toast
import io.reactivex.subjects.Subject
import javax.inject.Inject


/**
 *Created by rui on 2019/8/2
 */
class MainVModel @Inject constructor(app: BaseApplication) : BaseViewModel(app) {
    /**
     * 给ViewPager添加ObservableList数据
     */
    @Inject
    lateinit var items: ObservableList<Fragment>

    @Inject
    lateinit var finishAct: MutableLiveData<Event<Unit>>

    @Inject
    lateinit var keyWord: ObservableField<String>

    @Inject
    lateinit var closeKeyBoard: MutableLiveData<Event<Unit>>

    @Inject
    lateinit var subject: Subject<String>

    /**
     * 点击事件退出示例1
     */
    var listener: (v: View) -> Unit = {
        finishAct.value = Event(Unit)
        app.toast("点击了退出示例1")
    }

    fun addPage() {
        items.add(ProductImgFragment.newInstance(app, "F", "无图商品"))
        items.add(ProductImgFragment.newInstance(app, "T", "有图商品"))
    }

    /**
     * 点击事件退出示例2
     */
    fun closeAct(view: View) {
        finishAct.value = Event(Unit)
        app.toast("点击了退出示例2")
    }

    fun onSearch(v: View, keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
            //                Timber.d("---------->去搜索=>" + etSearchInput.getText().toString());
            subject.onNext(keyWord.get() ?: "")
            closeKeyBoard.value = Event(Unit)
            return true
        }
        return false
    }

    /**
     * 清楚输入的搜索关键词
     */
    fun clearkeyWord(view: View) {
        keyWord.set("")
        subject.onNext("")
    }

}