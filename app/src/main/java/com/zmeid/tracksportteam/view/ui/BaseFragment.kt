package com.zmeid.tracksportteam.view.ui

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import dagger.android.support.DaggerAppCompatDialogFragment

/**
 * It is a good practice to have a [BaseFragment] to hold common variables and methods of all fragments.
 */
abstract class BaseFragment : DaggerAppCompatDialogFragment() {

    fun showProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.GONE
    }

    /**
     * Shows a message to user in the center of activity.
     */
    fun showUserMessage(textView: TextView, message: String) {
        textView.apply {
            text = message
            visibility = View.VISIBLE
        }
    }

    fun hideUserMessageText(textView: TextView) {
        textView.visibility = View.GONE
    }

    fun showRetryButton(buttonRetry: Button) {
        buttonRetry.visibility = View.VISIBLE
    }

    fun hideRetryButton(buttonRetry: Button) {
        buttonRetry.visibility = View.GONE
    }
}