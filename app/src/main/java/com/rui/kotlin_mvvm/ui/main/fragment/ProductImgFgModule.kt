package com.rui.kotlin_mvvm.ui.main.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.rui.common.adapter.BaseRvAdapter
import com.rui.kotlin_mvvm.R
import com.rui.kotlin_mvvm.model.ProductModel
import com.rui.mvvm.di.module.LLModule
import com.rui.mvvm.di.module.ObservableModule
import com.rui.mvvm.di.scopes.FragmentScope
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
@Module(includes = [ProductImgFgModuleBinds::class, ObservableModule::class, LLModule::class])
class ProductImgFgModule {
    //这里定义其他需要提供的对象
    @Provides
    internal fun providesBaseRvAdapter(): BaseRvAdapter<ProductModel> =
        BaseRvAdapter(R.layout.item_product)

}

@Module
private abstract class ProductImgFgModuleBinds {

    @Binds
    @FragmentScope
    internal abstract fun fragment(fragment: ProductImgFragment): Fragment

    @Binds
    @IntoMap
    @ViewModelScope(ProductImgFgVModel::class)
    internal abstract fun viewModel(viewModel: ProductImgFgVModel): ViewModel

}