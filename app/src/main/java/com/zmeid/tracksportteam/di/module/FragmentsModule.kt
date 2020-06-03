package com.zmeid.tracksportteam.di.module

import com.zmeid.tracksportteam.view.ui.fragment.SearchTeamFragment
import com.zmeid.tracksportteam.view.ui.fragment.TeamEventHistoryFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Defines fragments where dependencies are going to be injected. New fragments should be added here.
 */
@Module
abstract class FragmentsModule {
    @ContributesAndroidInjector(modules = [ViewModelsModule::class, ContextModule::class, UtilsModule::class])
    abstract fun contributeSearchTeamFragment(): SearchTeamFragment

    @ContributesAndroidInjector(modules = [ViewModelsModule::class])
    abstract fun contributeTeamEventHistoryFragment(): TeamEventHistoryFragment
}