package com.rui.mvvm.di.module

import android.content.Context
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.LinearLayoutManager
import com.rui.mvvm.binding.RvOnListChangedCallback
import dagger.Module
import dagger.Provides

/**
 * 提供ObservableString、Observablelong、Observableint等基础类注入
 *  Created by rui on 2019/8/7
 */
@Module
class LLModule {

    @Provides
    internal fun providesLayoutManager(context: Context): LinearLayoutManager = LinearLayoutManager(context)

    @Provides
    internal fun providesRvOnListChangeCallback(): RvOnListChangedCallback<ObservableList<Any>> =
        RvOnListChangedCallback()

}