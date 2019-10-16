package com.rui.kotlin_mvvm.ui.main.activity

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.rui.kotlin_mvvm.ui.main.fragment.ProductImgFgModule
import com.rui.kotlin_mvvm.ui.main.fragment.ProductImgFragment
import com.rui.mvvm.Event
import com.rui.mvvm.di.module.ObservableModule
import com.rui.mvvm.di.scopes.ActivityContext
import com.rui.mvvm.di.scopes.ActivityScope
import com.rui.mvvm.di.scopes.FragmentScope
import com.rui.mvvm.di.scopes.ViewModelScope
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 *Created by rui on 2019/8/2
 * Kotlin中dagger的module写法与Java不同，原因如下：
 * Kotlin中的静态方法定义与Java不同，因此@Provides与@Binds所提供的对象需要分开两种：object（需要静态方法的时候）和 abstract的module来写
 * 通过includes去将两个module组合起来
 */
@Module(includes = [MainModuleBinds::class, ObservableModule::class])
class MainModule {
    //这里定义其他需要提供的对象

    @Provides
    internal fun providesObservableFgList(): ObservableList<Fragment> = ObservableArrayList()

    @Provides
    internal fun providesSubject(): Subject<String> = PublishSubject.create()

    @Provides
    internal fun providesMuLDUnit(): MutableLiveData<Event<Unit>> = MutableLiveData()

    @Provides
    internal fun providesFragmentManager(activity: FragmentActivity): FragmentManager =
        activity.supportFragmentManager

}

@Module
private abstract class MainModuleBinds {

    @FragmentScope
    @ContributesAndroidInjector(modules = [ProductImgFgModule::class])
    internal abstract fun fragmentInjector(): ProductImgFragment

    @Binds
    @IntoMap
    @ViewModelScope(MainVModel::class)
    internal abstract fun bindViewModel(viewModel: MainVModel): ViewModel

    @Binds
    @ActivityScope
    internal abstract fun activity(activity: MainActivity): FragmentActivity

    @Binds
    @ActivityScope
    @ActivityContext
    internal abstract fun activityContext(activity: FragmentActivity): Context
}