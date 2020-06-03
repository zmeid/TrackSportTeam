package com.zmeid.tracksportteam.di.module

import androidx.lifecycle.ViewModel
import com.zmeid.tracksportteam.di.ViewModelKey
import com.zmeid.tracksportteam.viewmodel.SearchTeamViewModel
import com.zmeid.tracksportteam.viewmodel.TeamEventHistoryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Enables dependency injection for viewModels. New viewModels should be added here with related activity/fragment.
 */
@Module
abstract class ViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(SearchTeamViewModel::class)
    abstract fun bindSearchTeamFragmentViewModel(searchTeamViewModel: SearchTeamViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TeamEventHistoryViewModel::class)
    abstract fun bindTeamEventHistoryFragmentViewModel(teamEventHistoryViewModel: TeamEventHistoryViewModel): ViewModel
}