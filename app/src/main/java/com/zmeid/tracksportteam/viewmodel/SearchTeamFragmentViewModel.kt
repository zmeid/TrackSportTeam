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

class SearchTeamFragmentViewModel @Inject constructor(private val teamRepository: TeamRepository) :
    ViewModel() {

    private val teamLastSearchWordMutable = MutableLiveData<String>()
    val teamLastSearchWord: LiveData<String> = teamLastSearchWordMutable

    private val teamSearchResultMutable: MutableLiveData<ApiResponseWrapper<List<Team>>> =
        MutableLiveData()
    val teamSearchResult: LiveData<ApiResponseWrapper<List<Team>>> = teamSearchResultMutable

    /**
     * This is the method used by fragment to search team.
     */
    fun searchTeam(word: String) {
        if (word != teamLastSearchWordMutable.value) {
            viewModelScope.launch(Dispatchers.IO) {
                teamSearchResultMutable.postValue(ApiResponseWrapper.loading())
                try {
                    val teamList = teamRepository.searchTeam(word)
                    teamSearchResultMutable.postValue(
                        ApiResponseWrapper.success(
                            teamList
                        )
                    )
                } catch (e: Exception) {
                    teamSearchResultMutable.postValue(ApiResponseWrapper.error(exception = e))
                }
            }
            teamLastSearchWordMutable.value = word
        }
    }
}