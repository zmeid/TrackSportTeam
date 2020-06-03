package com.zmeid.tracksportteam.view.ui.fragment

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.ListAdapter
import com.zmeid.tracksportteam.R
import com.zmeid.tracksportteam.util.ApiResponseWrapper
import com.zmeid.tracksportteam.util.ErrorMessageGenerator
import com.zmeid.tracksportteam.view.adapter.TeamAdapter
import com.zmeid.tracksportteam.view.adapter.TeamEventHistoryAdapter
import com.zmeid.tracksportteam.view.ui.activity.MainActivity
import dagger.android.support.DaggerAppCompatDialogFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * It is a good practice to have a [BaseFragment] to hold common variables and methods of all fragments.
 */
abstract class BaseFragment : DaggerAppCompatDialogFragment() {

    private fun showProgressBar() {
        activity?.progress_bar?.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        activity?.progress_bar?.visibility = View.GONE
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

    private fun showRetryButton(buttonRetry: Button) {
        buttonRetry.visibility = View.VISIBLE
    }

    private fun hideRetryButton(buttonRetry: Button) {
        buttonRetry.visibility = View.GONE
    }

    /**
     * Handles team search result according to [ApiResponseWrapper]'s status.
     *
     * If [ApiResponseWrapper.Status.LOADING], it shows progress bar, hides user messages and hides retry button.
     * If [ApiResponseWrapper.Status.SUCCESS], updates adapter with received data, hides progress bar, hides user messages and hides retry button. If the received data is empty; shows a message to user.
     * If [ApiResponseWrapper.Status.ERROR], it shows the error message and retry button. Clears recyclerview.
     */
    fun <T> handleAPIResult(
        apiResponseWrapper: ApiResponseWrapper<List<T>>,
        userMessageTextView: TextView,
        retryButton: Button,
        searchTeamAdapter: ListAdapter<T, TeamAdapter.TeamViewHolder>? = null,
        teamEventHistoryEventHistoryAdapter: ListAdapter<T, TeamEventHistoryAdapter.TeamEventHistoryViewHolder>? = null
    ) {
        when (apiResponseWrapper.status) {
            ApiResponseWrapper.Status.LOADING -> {
                showProgressBar()
                hideUserMessageText(userMessageTextView)
                hideRetryButton(retryButton)
            }
            ApiResponseWrapper.Status.SUCCESS -> {
                hideProgressBar()
                hideUserMessageText(userMessageTextView)
                hideRetryButton(retryButton)
                val list = apiResponseWrapper.data
                searchTeamAdapter?.submitList(list)
                teamEventHistoryEventHistoryAdapter?.submitList(list)
                if (list == null || list.isEmpty()) showUserMessage(
                    userMessageTextView,
                    getString(R.string.nothing_found)
                )
            }
            ApiResponseWrapper.Status.ERROR -> {
                hideProgressBar()
                val errorMessageGenerator = ErrorMessageGenerator(requireContext())
                val errorMessage =
                    errorMessageGenerator.generateErrorMessage(apiResponseWrapper.exception!!)
                searchTeamAdapter?.submitList(null)
                teamEventHistoryEventHistoryAdapter?.submitList(null)
                showUserMessage(userMessageTextView, errorMessage)
                showRetryButton(retryButton)
            }
        }
    }

    fun getSupportActionBar(): ActionBar? {
        val activity = activity as MainActivity
        return activity.supportActionBar
    }
}