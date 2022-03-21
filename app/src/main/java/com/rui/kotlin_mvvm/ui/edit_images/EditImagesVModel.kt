package com.rui.kotlin_mvvm.ui.edit_images

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.viewModelScope
import com.luck.picture.lib.entity.LocalMedia
import com.rui.common.base.BaseListVModel
import com.rui.mvvm.BaseApplication
import com.rui.retrofit2.basemodel.ResultModel
import io.reactivex.Single
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *Created by rui on 2019/8/2
 */
class EditImagesVModel @Inject constructor(app: BaseApplication) :
    BaseListVModel<LocalMedia>(app) {

    override fun getDataOB(): Single<ResultModel<LocalMedia>> {
        return Single.never()
    }
    @Inject
    lateinit var currentPostion: ObservableInt
    @Inject
    lateinit var rvItemPosition: ObservableInt
    @Inject
    lateinit var isShowSave: ObservableBoolean

    init {
        viewModelScope.launch {
            // Coroutine that will be canceled when the ViewModel is cleared.
        }
    }

    fun deleteItem(pos: Int) {
//        items.value.removeAt(pos)
        isShowSave.set(true)
    }
}