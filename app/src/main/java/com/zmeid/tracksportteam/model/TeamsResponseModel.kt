package com.zmeid.tracksportteam.model

import com.google.gson.annotations.SerializedName

private const val SN_TEAM_LIST = "teams"

/**
 *   TheSportsDb API returns teams as list with [SN_TEAM_LIST] tag. [TeamsResponseModel] is used to hold and map list of [Team]s.
 */
data class TeamsResponseModel(@SerializedName(SN_TEAM_LIST) val teamList: List<Team>)