package com.zmeid.tracksportteam.di.module

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.zmeid.tracksportteam.util.DialogUtils
import com.zmeid.tracksportteam.util.ErrorMessageGenerator
import dagger.Module
import dagger.Provides

/**
 * Provides utility objects.
 */
@Module
class UtilsModule {
    @Provides
    fun providesLayoutManager(fragment: Fragment) = LinearLayoutManager(fragment.context)

    @Provides
    fun providesApiErrorMessageGenerator(fragment: Fragment) =
        ErrorMessageGenerator(fragment.requireContext())

    @Provides
    fun providesDialogUtils(fragment: Fragment) = DialogUtils(fragment)
}