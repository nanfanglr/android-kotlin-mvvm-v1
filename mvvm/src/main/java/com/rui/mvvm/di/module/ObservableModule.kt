package com.rui.mvvm.di.module

import androidx.databinding.*
import dagger.Module
import dagger.Provides

/**
 * 提供ObservableString、Observablelong、Observableint等基础类注入
 *  Created by rui on 2019/8/7
 */
@Module
class ObservableModule {

    @Provides
    internal fun providesObservableFieldString(): ObservableField<String> = ObservableField()

    @Provides
    internal fun providesObservableInt(): ObservableInt = ObservableInt()

    @Provides
    internal fun providesObservableLong(): ObservableLong = ObservableLong()

    @Provides
    internal fun providesObservableFloat(): ObservableFloat = ObservableFloat()

    @Provides
    internal fun providesObservableDouble(): ObservableDouble = ObservableDouble()

    @Provides
    internal fun providesObservableBoolean(): ObservableBoolean = ObservableBoolean()


}