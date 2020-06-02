package com.zmeid.tracksportteam.repository.webservice

import com.zmeid.tracksportteam.model.TeamsResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

private const val GET_PATH_SEARCH_TEAMS = "searchteams.php"
private const val QUERY_PARAM_TEAM = "t"

/**
 * Holds TheSportsDb calls which will be used in [com.zmeid.tracksportteam.repository.TeamRepository]
 */
interface TrackSportTeamService {
    @GET(GET_PATH_SEARCH_TEAMS)
    suspend fun searchTeam(@Query(QUERY_PARAM_TEAM) team: String): TeamsResponseModel
}