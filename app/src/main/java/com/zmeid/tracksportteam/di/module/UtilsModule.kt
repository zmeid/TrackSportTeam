package com.zmeid.tracksportteam.di.module

import android.app.Application
import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
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

    @Provides
    fun providesSharedPref(appContext: Application): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(appContext)
}