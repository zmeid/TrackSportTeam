package com.zmeid.tracksportteam.model

import com.google.gson.annotations.SerializedName

private const val idTeam = "idTeam"
private const val strTeam = "strTeam"
private const val strLeague = "strLeague"
private const val strDescriptionEN = "strDescriptionEN"
private const val strStadium = "strStadium"
private const val strSport = "strSport"
private const val strCountry = "strCountry"
private const val strTeamBadge = "strTeamBadge"

/**
 *  [Team] data class is used by Retrofit. It holds variables needed to map API responses.
 */
data class Team(
    @SerializedName(idTeam) val id: Int,
    @SerializedName(strTeam) val teamName: String,
    @SerializedName(strDescriptionEN) val description: String,
    @SerializedName(strSport) val sport: String,
    @SerializedName(strLeague) val league: String,
    @SerializedName(strStadium) val stadium: String,
    @SerializedName(strCountry) val country: String,
    @SerializedName(strTeamBadge) val teamBadge: String
)