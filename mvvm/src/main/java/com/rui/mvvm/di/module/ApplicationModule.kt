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

package com.rui.mvvm.di.module

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import com.rui.mvvm.BaseApplication
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Singleton
    @Provides
    fun provideApplicationContext(app: BaseApplication): Context {
        return app
    }

    @Singleton
    @Provides
    @Named("debug")
    fun provideIsDebug(app: BaseApplication): Boolean {
        return app.isDebug()
    }


//    @Provides
//    @Singleton
//    internal fun providesPropertyManager(assetManager: AssetManager, app: BaseApplication): PropertiesManager {
//        return PropertiesManager(assetManager, app.isDebug())
//    }

    @Provides
    @Singleton
    fun providesResources(application: BaseApplication): Resources {
        return application.resources
    }

    @Provides
    @Singleton
    fun providesAssetManager(resources: Resources): AssetManager {
        return resources.assets
    }

}
