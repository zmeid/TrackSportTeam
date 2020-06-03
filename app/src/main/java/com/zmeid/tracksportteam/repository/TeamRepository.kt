package com.zmeid.tracksportteam.repository

import com.zmeid.tracksportteam.model.Team
import com.zmeid.tracksportteam.model.TeamEventHistory
import com.zmeid.tracksportteam.repository.webservice.TrackSportTeamService
import javax.inject.Inject

class TeamRepository @Inject constructor(private val trackSportTeamService: TrackSportTeamService) {

    suspend fun searchTeam(word: String): List<Team> {
        return trackSportTeamService.searchTeam(word).teamList
    }

    suspend fun getTeamEventHistory(id: Int): List<TeamEventHistory> {
        return trackSportTeamService.getTeamEventHistory(id).eventHistoryList
    }
}