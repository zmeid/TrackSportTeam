package com.zmeid.tracksportteam.di.module

import androidx.fragment.app.Fragment
import com.zmeid.tracksportteam.view.ui.SearchTeamFragment
import dagger.Binds
import dagger.Module

/**
 * This module provides contexts. For ex for [UtilsModule].
 */
@Module
abstract class ContextModule {
    @Binds
    abstract fun providesSearchTeamFragment(fragmentsModule: SearchTeamFragment): Fragment
}