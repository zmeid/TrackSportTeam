package com.zmeid.tracksportteam.di.module

import com.zmeid.tracksportteam.view.ui.SearchTeamFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Defines fragments where dependencies are going to be injected. New fragments should be added here.
 */
@Module
abstract class FragmentsModule {
    @ContributesAndroidInjector(modules = [ViewModelsModule::class, ContextModule::class, UtilsModule::class])
    abstract fun contributeSearchTeamFragment(): SearchTeamFragment
}