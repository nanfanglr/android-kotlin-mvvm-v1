package com.rui.kotlin_mvvm.di.module

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.support.v4.app.FragmentActivity
import com.luck.picture.lib.entity.LocalMedia
import com.rui.kotlin_mvvm.di.vmodel.ProductDtlVModel
import com.rui.kotlin_mvvm.model.ColorModel
import com.rui.kotlin_mvvm.ui.ProductDtlActivity
import com.rui.mvvm.binding.VPOnListChangedCallback
import com.rui.mvvm.di.module.LLModule
import com.rui.mvvm.di.module.ObservableModule
import com.rui.mvvm.di.scopes.ActivityContext
import com.rui.mvvm.di.scopes.ActivityScope
import com.rui.mvvm.di.scopes.ViewModelScope
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap


/**
 *Created by rui on 2019/8/2
 * Kotlin中dagger的module写法与Java不同，原因如下：
 * Kotlin中的静态方法定义与Java不同，因此@Provides与@Binds所提供的对象需要分开两种：object（需要静态方法的时候）和 abstract的module来写
 * 通过includes去将两个module组合起来
 */
@Module(includes = [ProductDtlModuleBinds::class, ObservableModule::class, LLModule::class])
class ProductDtlModule {
    //这里定义其他需要提供的对象
//    @Provides
//    internal fun providesBaseRvAdapter(): BaseRvAdapter<ProductModel> =
//        BaseRvAdapter(R.layout.item_product)
    @Provides
    internal fun providesVPOnListChangedCallback(): VPOnListChangedCallback<ObservableList<ColorModel>> =
        VPOnListChangedCallback()


    @Provides
    fun providesLocalMediaList(): ObservableList<LocalMedia> {
        return ObservableArrayList()
    }

}

@Module
private abstract class ProductDtlModuleBinds {

    @Binds
    @IntoMap
    @ViewModelScope(ProductDtlVModel::class)
    internal abstract fun bindViewModel(viewModel: ProductDtlVModel): ViewModel

    @Binds
    @ActivityScope
    internal abstract fun activity(activity: ProductDtlActivity): FragmentActivity

    @Binds
    @ActivityScope
    @ActivityContext
    internal abstract fun activityContext(activity: FragmentActivity): Context

}