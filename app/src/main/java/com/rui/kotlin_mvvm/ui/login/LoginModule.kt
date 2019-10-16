package com.rui.kotlin_mvvm.ui.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.support.v4.app.FragmentActivity
import com.rui.mvvm.Event
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
@Module(includes = [LoginModuleBinds::class, ObservableModule::class])
class LoginModule {
    //这里定义其他需要提供的对象
//    @Provides
//    internal fun providesObservableFieldString(): ObservableField<String> = ObservableField()
    @Provides
    internal fun providesMLDUit(): MutableLiveData<Event<Unit>> = MutableLiveData()


}

@Module
private abstract class LoginModuleBinds {

    @Binds
    @IntoMap
    @ViewModelScope(LoginVModel::class)
    internal abstract fun bindViewModel(viewModel: LoginVModel): ViewModel

    @Binds
    @ActivityScope
    internal abstract fun activity(activity: LoginActivity): FragmentActivity

    @Binds
    @ActivityScope
    @ActivityContext
    internal abstract fun activityContext(activity: FragmentActivity): Context
}