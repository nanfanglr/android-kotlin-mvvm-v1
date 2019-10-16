package com.rui.kotlin_mvvm.repository

import com.rui.kotlin_mvvm.model.UserModel
import com.rui.kotlin_mvvm.netservice.NetService
import com.rui.retrofit2.basemodel.ResultModel
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject
import javax.inject.Singleton

/**
 *Created by rui on 2019/8/26
 */
@Singleton
class UserRepository @Inject constructor(private val netService: NetService) {

    /**
     * 用户信息
     */
    private var userInfo: UserModel? = null

    /**
     * 更新用户信息的数据源
     * 在需要更新用户新的页面，注入UserInfoRepository，获取到该对象后即可更新数据
     */
    private val subject: Subject<UserModel> by lazy {
        PublishSubject.create<UserModel>()
    }

    /**
     * 更新用户信息
     *
     * @param user
     */
    fun updateUserInfo(user: UserModel?) {
        user?.let {
            userInfo = it
            subject.onNext(it)
        }
    }

    /**
     * 更新用户头像
     *
     * @param user
     */
    fun updateHeadUrl(url: String) {
        userInfo?.run {
            headUrl = url
            subject.onNext(this)
        }
    }

    /**
     * 获取当前登录的用户信息
     *
     * @return
     */
    fun getUserInfoOB(): Single<UserModel> {
        return if (userInfo != null) Single.just<UserModel>(userInfo)
        else Single.error<UserModel>(Throwable("你还没有登录APP"))
    }

    fun getLoginOB(userKey: String?, userPswd: String?): Single<ResultModel<UserModel>> {
        return Single.create<ResultModel<UserModel>> {
            val resultModel = ResultModel<UserModel>()
            resultModel.success = true
            val userModel = UserModel()
            userModel.apiAutoToken = "1234156s4dfas4d6fasd546546as4d6f5a4s6"
            userModel.name = "Jay"
            userModel.id = 1
            resultModel.obj = userModel
            it.onSuccess(resultModel)
        }
            .map {
                if (it.success) updateUserInfo(it.obj)
                it
            }
//                return netService.login(userKey, userPswd)
    }

}