package com.rui.mvvm.di.scopes;


import androidx.lifecycle.ViewModel;

import dagger.MapKey;

import java.lang.annotation.*;

/**
 * Created by rui on 2018/10/29.
 * Dagger ViewModelScope
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@MapKey
public @interface ViewModelScope {
    Class<? extends ViewModel> value();
}
