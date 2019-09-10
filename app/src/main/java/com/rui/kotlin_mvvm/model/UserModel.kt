package com.rui.kotlin_mvvm.model

import com.rui.retrofit2.basemodel.BaseModel

/**
 *Created by rui on 2019/8/10
 */
data class UserModel(
    var id: Int = 0,
    var headUrl: String = "",
    var name: String = "",
    var code: String = "",
    var apiAutoToken: String = ""
) : BaseModel()


