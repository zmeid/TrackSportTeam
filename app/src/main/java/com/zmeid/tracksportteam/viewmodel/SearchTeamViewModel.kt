package com.zmeid.tracksportteam.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zmeid.tracksportteam.model.Team
import com.zmeid.tracksportteam.repository.TeamRepository
import com.zmeid.tracksportteam.util.ApiResponseWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchTeamViewModel @Inject constructor(private val teamRepository: TeamRepository) :
    ViewModel() {

    private val teamLastSearchWordMutable = MutableLiveData<String>()
    val teamLastSearchWord: LiveData<String> = teamLastSearchWordMutable

    private val teamSearchResultMutable: MutableLiveData<ApiResponseWrapper<List<Team>>> =
        MutableLiveData()
    val teamSearchResult: LiveData<ApiResponseWrapper<List<Team>>> = teamSearchResultMutable

    /**
     * This is the method used by fragment to search team.
     *
     * If [force] is true, even if the word is equal to previous searched word, API call will be made.
     */
    fun searchTeam(word: String, force: Boolean) {
        if (force || word != teamLastSearchWordMutable.value) {
            viewModelScope.launch(Dispatchers.IO) {
                teamSearchResultMutable.postValue(ApiResponseWrapper.loading())
                try {
                    val teamList = teamRepository.searchTeam(word)
                    teamSearchResultMutable.postValue(ApiResponseWrapper.success(teamList))
                } catch (e: Exception) {
                    teamSearchResultMutable.postValue(ApiResponseWrapper.error(exception = e))
                }
            }
            teamLastSearchWordMutable.value = word
        }
    }
}