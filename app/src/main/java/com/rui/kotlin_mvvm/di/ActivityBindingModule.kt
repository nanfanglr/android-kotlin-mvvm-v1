/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rui.kotlin_mvvm.di

import com.rui.kotlin_mvvm.ui.edit_images.EditImagesActivity
import com.rui.kotlin_mvvm.ui.edit_images.EditImagesModule
import com.rui.kotlin_mvvm.ui.login.LoginActivity
import com.rui.kotlin_mvvm.ui.login.LoginModule
import com.rui.kotlin_mvvm.ui.main.activity.MainModule
import com.rui.kotlin_mvvm.ui.main.activity.MainActivity
import com.rui.kotlin_mvvm.ui.product_dtl.ProductDtlActivity
import com.rui.kotlin_mvvm.ui.product_dtl.ProductDtlModule
import com.rui.mvvm.di.scopes.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * We want Dagger.Android to create a Subcomponent which has a parent Component of whichever module
 * ActivityBindingModule is on, in our case that will be [AppComponent]. You never
 * need to tell [AppComponent] that it is going to have all these subcomponents
 * nor do you need to tell these subcomponents that [AppComponent] exists.
 * We are also telling Dagger.Android that this generated SubComponent needs to include the
 * specified modules and be aware of a scope annotation [@ActivityScoped].
 * When Dagger.Android annotation processor runs it will create 2 subcomponents for us.
 */
@Module
abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [LoginModule::class])
    internal abstract fun loginActivity(): LoginActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainModule::class])
    internal abstract fun mainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [ProductDtlModule::class])
    internal abstract fun productDtlActivity(): ProductDtlActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [EditImagesModule::class])
    internal abstract fun editImagesActivity(): EditImagesActivity

}
