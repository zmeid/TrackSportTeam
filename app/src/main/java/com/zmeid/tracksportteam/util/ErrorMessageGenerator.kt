package com.zmeid.tracksportteam.util

import android.content.Context
import com.zmeid.tracksportteam.R
import timber.log.Timber
import java.net.UnknownHostException

/**
 * This class is to be injected where error messages need to be generated.
 */
class ErrorMessageGenerator constructor(private val context: Context) {
    /**
     * Generates error messages which are going to be displayed to user based on exception received.
     */
    fun generateErrorMessage(exception: Exception): String {
        Timber.e(exception)
        var stringResId = R.string.search_there_is_an_error
        when (exception) {
            is UnknownHostException -> {
                stringResId = R.string.no_internet_error
            }
        }
        return String.format(context.getString(stringResId), exception.message)
    }
}