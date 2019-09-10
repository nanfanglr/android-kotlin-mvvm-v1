/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rui.kotlin_mvvm.di

import com.rui.kotlin_mvvm.App
import com.rui.kotlin_mvvm.di.module.ActivityBindingModule
import com.rui.kotlin_mvvm.di.module.SingleTonModule
import com.rui.mvvm.BaseApplication
import com.rui.mvvm.di.module.ApplicationModule
import com.rui.mvvm.di.module.ViewModelFactoryModule
import com.rui.retrofit2.module.RetrofitModule

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Main component for the application.
 *
 * See the `TestApplicationComponent` used in UI tests.
 */
@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        ViewModelFactoryModule::class,
        RetrofitModule::class,
        SingleTonModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance baseApplication: BaseApplication): ApplicationComponent
    }
}
