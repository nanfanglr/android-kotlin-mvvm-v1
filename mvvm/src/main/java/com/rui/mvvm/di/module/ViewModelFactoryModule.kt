package com.rui.mvvm.di.module


import android.arch.lifecycle.ViewModelProvider
import com.rui.mvvm.vmodel.ViewModelFactory
import dagger.Binds
import dagger.Module

/**
 *Created by rui on 2019/8/1
 */
@Module
abstract class ViewModelFactoryModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}