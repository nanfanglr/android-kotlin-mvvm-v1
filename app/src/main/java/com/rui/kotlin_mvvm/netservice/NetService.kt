package com.rui.kotlin_mvvm.netservice

import com.rui.kotlin_mvvm.model.UserModel
import com.rui.retrofit2.basemodel.ResultModel
import io.reactivex.Single
import retrofit2.http.*

/**
 * retrofit这里定义接口
 * Created by rui on 2019/2/12
 */
interface NetService {

    /**
     * pos用户登陆
     *
     * @param userKey  手机号
     * @param userPswd 密码
     * @return
     */
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("userKey") userKey: String,
        @Field("userPswd") userPswd: String
    ): Single<ResultModel<UserModel>>

    //    /**
    //     * @param size    每页的条数
    //     * @param page    页码
    //     * @param keyword 搜索关键字：sku编码，内部码，国标码，
    //     * @return
    //     */
    //    @FormUrlEncoded
    //    @POST("pos/prods")
    //    Single<ResultModel<ProductModel>> searchProds(
    //            @Field("size") int size,
    //            @Field("page") int page,
    //            @Field("keyword") String keyword);

    /**
     * 门店商品搜索
     *
     * @param size    每页的条数
     * @param page    页码
     * @param keyword 搜索关键字：sku编码，内部码，国标码，
     * @return
     */
    @GET("pos/prods")
    fun searchProds(
        @Query("size") size: Int,
        @Query("page") page: Int,
        @Query("keyword") keyword: String
    ): Single<ResultModel<Any>>


}
