package com.rui.mvvm.di.scopes

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Qualifier

/**
 * So that we are able to tell apart application contexts from activity ones in the dagger graph.
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
annotation class ApplicationContext 