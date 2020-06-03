package com.zmeid.tracksportteam.model

import com.google.gson.annotations.SerializedName

private const val idEvent = "idEvent"
private const val strEvent = "strEvent"
private const val strLeague = "strLeague"
private const val strSeason = "strSeason"
private const val intHomeScore = "intHomeScore"
private const val intAwayScore = "intAwayScore"
private const val dateEvent = "dateEvent"
private const val strTime = "strTime"

/**
 *  [TeamEventHistory] data class is used by Retrofit. It holds variables needed to map API response.
 */
data class TeamEventHistory(
    @SerializedName(idEvent) val id: Int,
    @SerializedName(strEvent) val event: String,
    @SerializedName(strLeague) val league: String,
    @SerializedName(strSeason) val season: String,
    @SerializedName(intHomeScore) val homeScore: Int,
    @SerializedName(intAwayScore) val awayScore: Int,
    @SerializedName(dateEvent) val eventDate: String,
    @SerializedName(strTime) val eventTime: String
)