package com.zmeid.tracksportteam.di.module

import androidx.fragment.app.Fragment
import com.zmeid.tracksportteam.util.DialogUtils
import dagger.Module
import dagger.Provides

/**
 * Provides utility objects.
 */
@Module
class UtilsModule {
    @Provides
    fun providesDialogUtils(fragment: Fragment) = DialogUtils(fragment)
}