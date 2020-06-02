package com.zmeid.tracksportteam.di.module

import androidx.lifecycle.ViewModel
import com.zmeid.tracksportteam.di.ViewModelKey
import com.zmeid.tracksportteam.viewmodel.SearchTeamFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Enables dependency injection for viewModels. New viewmodels should be added here with related activity/fragment.
 */
@Module
abstract class ViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(SearchTeamFragmentViewModel::class)
    abstract fun bindSearchTeamFragmentViewModel(searchTeamFragmentViewModel: SearchTeamFragmentViewModel): ViewModel
}