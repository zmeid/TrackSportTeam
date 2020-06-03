package com.zmeid.tracksportteam.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zmeid.tracksportteam.databinding.TeamEventHistoryResultRowBinding
import com.zmeid.tracksportteam.model.TeamEventHistory
import javax.inject.Inject

/**
 * Used as adapter of team event history recycler view. View binding is used to bind the views.
 *
 * It uses Eugene W. Myers's difference algorithm to calculate the minimal number of updates to convert one list into another.
 *
 */
class TeamEventHistoryAdapter @Inject constructor() :
    ListAdapter<TeamEventHistory, TeamEventHistoryAdapter.TeamEventHistoryViewHolder>(
        TeamEventHistoryListDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamEventHistoryViewHolder {
        return TeamEventHistoryViewHolder(
            // Use view binding
            TeamEventHistoryResultRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holderEventHistory: TeamEventHistoryViewHolder, position: Int) {
        val teamEventHistory = getItem(position)
        holderEventHistory.bind(teamEventHistory)
    }

    class TeamEventHistoryViewHolder(private val binding: TeamEventHistoryResultRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(teamEventHistory: TeamEventHistory) {
            binding.apply {
                textViewEvent.text = teamEventHistory.event
                textViewDate.text = teamEventHistory.eventDate
                textViewTime.text = teamEventHistory.eventTime
                textViewLeague.text = teamEventHistory.league
                textViewSeason.text = teamEventHistory.season
                textViewScore.text = "${teamEventHistory.homeScore} - ${teamEventHistory.awayScore}"
            }
        }
    }
}

private class TeamEventHistoryListDiffCallback : DiffUtil.ItemCallback<TeamEventHistory>() {
    override fun areItemsTheSame(oldItem: TeamEventHistory, newItem: TeamEventHistory): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TeamEventHistory, newItem: TeamEventHistory): Boolean {
        return oldItem == newItem
    }
}