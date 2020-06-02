package com.zmeid.tracksportteam.di.component

import android.app.Application
import com.zmeid.tracksportteam.BaseApplication
import com.zmeid.tracksportteam.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * AppComponent exist for the life time of application. It has the list of modules which are going to be used by application.
 */
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModuleFactoryModule::class,
        FragmentsModule::class,
        WebServiceModule::class,
        UtilsModule::class,
        ContextModule::class
    ]
)
@Singleton
interface AppComponent : AndroidInjector<BaseApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}