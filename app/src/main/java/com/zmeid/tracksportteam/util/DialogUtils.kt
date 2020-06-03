package com.zmeid.tracksportteam.util

import android.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.zmeid.tracksportteam.R
import com.zmeid.tracksportteam.view.ui.fragment.BaseFragment
import com.zmeid.tracksportteam.view.ui.fragment.SearchTeamFragment
import javax.inject.Inject

/**
 * Holds all dialog related functions. This class is lifecycle-aware. Which means, we don't have to take care of when activity life cycle changes.
 * It will be dismissed automatically when activity/fragment is destroyed and window leak will be prevented.
 */
class DialogUtils @Inject constructor(
    private val fragment: Fragment
) : LifecycleObserver {

    private var alertDialogAddToFavorites: AlertDialog? = null

    init {
        (fragment as BaseFragment).lifecycle.addObserver(this)
    }

    fun createAddToFavoriteDialog(): AlertDialog {
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(fragment.context)
        alertDialogBuilder.setTitle(R.string.add_to_favorites)
        alertDialogBuilder.setMessage(R.string.favorites_description)
        alertDialogBuilder.setPositiveButton(
            R.string.activate_premium
        ) { _, _ ->
            run {
                (fragment as SearchTeamFragment).activatePremiumClicked()
                dismissAlertDialogSortingPref()
            }
        }
        alertDialogBuilder.setNegativeButton(
            R.string.not_now
        ) { _, _ -> dismissAlertDialogSortingPref() }

        alertDialogAddToFavorites = alertDialogBuilder.create()
        return alertDialogAddToFavorites!!
    }

    interface ActivatePremiumClickedListener {
        fun activatePremiumClicked()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun dismissAlertDialogSortingPref() {
        alertDialogAddToFavorites?.dismiss()
    }
}