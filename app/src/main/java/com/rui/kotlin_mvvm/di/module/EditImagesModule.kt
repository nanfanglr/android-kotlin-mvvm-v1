package com.rui.kotlin_mvvm.di.module

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.GridLayoutManager
import com.luck.picture.lib.entity.LocalMedia
import com.rui.kotlin_mvvm.di.vmodel.EditImagesVModel
import com.rui.kotlin_mvvm.ui.EditImagesActivity
import com.rui.mvvm.binding.VPOnListChangedCallback
import com.rui.mvvm.di.module.LLModule
import com.rui.mvvm.di.module.ObservableModule
import com.rui.mvvm.di.scopes.ActivityContext
import com.rui.mvvm.di.scopes.ActivityScope
import com.rui.mvvm.di.scopes.ViewModelScope
import com.rui.toolkit.DisplayUtils
import com.rui.viewkit.GridSpacingItemDecoration
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
@Module(includes = [EditImagesModuleBinds::class, ObservableModule::class, LLModule::class])
class EditImagesModule {
    //这里定义其他需要提供的对象

    @Provides
    internal fun providesVPOnListChangedCallback(): VPOnListChangedCallback<ObservableList<LocalMedia>> =
        VPOnListChangedCallback()

    @Provides
    internal fun providesLayoutManager(context: Context): GridLayoutManager =
        GridLayoutManager(context, 4)

    @Provides
    fun providesLocalMediaList(): ObservableList<LocalMedia> {
        return ObservableArrayList()
    }

    @Provides
    fun providesItemDecoration(context: Context): GridSpacingItemDecoration {
        return GridSpacingItemDecoration(4, DisplayUtils.dip2px(context, 10f), false)
    }

}

@Module
private abstract class EditImagesModuleBinds {

    @Binds
    @IntoMap
    @ViewModelScope(EditImagesVModel::class)
    internal abstract fun bindViewModel(viewModel: EditImagesVModel): ViewModel

    @Binds
    @ActivityScope
    internal abstract fun activity(activity: EditImagesActivity): FragmentActivity

    @Binds
    @ActivityScope
    @ActivityContext
    internal abstract fun activityContext(activity: FragmentActivity): Context

}