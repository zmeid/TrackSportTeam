package com.zmeid.tracksportteam.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zmeid.tracksportteam.model.TeamEventHistory
import com.zmeid.tracksportteam.repository.TeamRepository
import com.zmeid.tracksportteam.util.ApiResponseWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class TeamEventHistoryViewModel @Inject constructor(private val teamRepository: TeamRepository) :
    ViewModel() {

    private val teamEventHistoryResultMutable: MutableLiveData<ApiResponseWrapper<List<TeamEventHistory>>> =
        MutableLiveData()
    val teamEventHistoryResult: LiveData<ApiResponseWrapper<List<TeamEventHistory>>> =
        teamEventHistoryResultMutable

    /**
     * This is the method used by fragment to get team history.
     */
    fun getTeamHistory(teamId: Int) {
        if (teamEventHistoryResultMutable.value != null) return
        viewModelScope.launch(Dispatchers.IO) {
            teamEventHistoryResultMutable.postValue(ApiResponseWrapper.loading())
            try {
                val teamEventHistoryList = teamRepository.getTeamEventHistory(teamId)
                teamEventHistoryResultMutable.postValue(
                    ApiResponseWrapper.success(teamEventHistoryList)
                )
            } catch (e: Exception) {
                teamEventHistoryResultMutable.postValue(ApiResponseWrapper.error(exception = e))
            }
        }
    }
}