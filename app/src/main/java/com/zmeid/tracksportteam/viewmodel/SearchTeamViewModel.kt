package com.zmeid.tracksportteam.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zmeid.tracksportteam.model.Team
import com.zmeid.tracksportteam.repository.TeamRepository
import com.zmeid.tracksportteam.util.ApiResponseWrapper
import com.zmeid.tracksportteam.util.SHARED_PREF_HISTORY_VIEW
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchTeamViewModel @Inject constructor(
    private val teamRepository: TeamRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private var teamLastSearchWordMutable = ""

    private val shouldShowAdsMutable: MutableLiveData<Boolean> =
        MutableLiveData()
    val shouldShowAds: LiveData<Boolean> = shouldShowAdsMutable

    private val teamSearchResultMutable: MutableLiveData<ApiResponseWrapper<List<Team>>> =
        MutableLiveData()
    val teamSearchResult: LiveData<ApiResponseWrapper<List<Team>>> = teamSearchResultMutable

    /**
     * This is the method used by fragment to search team.
     *
     * If [force] is true, even if the word is equal to previous searched word, API call will be made.
     */
    fun searchTeam(word: String, force: Boolean) {
        if (force || word != teamLastSearchWordMutable) {
            viewModelScope.launch(Dispatchers.IO) {
                teamSearchResultMutable.postValue(ApiResponseWrapper.loading())
                try {
                    val teamList = teamRepository.searchTeam(word)
                    teamSearchResultMutable.postValue(ApiResponseWrapper.success(teamList))
                } catch (e: Exception) {
                    teamSearchResultMutable.postValue(ApiResponseWrapper.error(exception = e))
                }
            }
            teamLastSearchWordMutable = word
        }
    }

    /**
     * The count of user's event history fragment visits are stored as reminder of 3.
     *
     * [SHARED_PREF_HISTORY_VIEW] is used to decide whether to show ads to user or not. If the count is zero, we show ads when user navigates to TeamEventHistoryFragment.
     */
    fun checkIfItsTimeForAds() {
        viewModelScope.launch(Dispatchers.Default) {
            val count = sharedPreferences.getInt(SHARED_PREF_HISTORY_VIEW, 0)
            shouldShowAdsMutable.postValue(count == 0)
        }
    }

    /**
     * Increase the count by 1 and put in [sharedPreferences] as reminder of 3.
     */
    fun increaseEventHistoryViewCount() {
        viewModelScope.launch(Dispatchers.Default) {
            val count = sharedPreferences.getInt(SHARED_PREF_HISTORY_VIEW, 0)
            val newCount = (count + 1) % 3
            sharedPreferences.edit().putInt(SHARED_PREF_HISTORY_VIEW, newCount).apply()
        }
    }
}