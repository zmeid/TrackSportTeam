package com.zmeid.tracksportteam.model

import com.google.gson.annotations.SerializedName

private const val SN_RESULTS = "results"

/**
 *   TheSportsDb API returns last 5 events as list with [SN_RESULTS] tag. [TeamEventHistoryResponseModel] is used to hold and map list of [TeamEventHistory]s.
 */
data class TeamEventHistoryResponseModel(@SerializedName(SN_RESULTS) val eventHistoryList: List<TeamEventHistory>)