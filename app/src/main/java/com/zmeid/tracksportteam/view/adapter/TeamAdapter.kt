package com.zmeid.tracksportteam.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zmeid.tracksportteam.R
import com.zmeid.tracksportteam.databinding.TeamSearchResultRowBinding
import com.zmeid.tracksportteam.model.Team
import javax.inject.Inject

/**
 * Used as adapter of team recycler view. View binding is used to bind the views.
 *
 * It uses Eugene W. Myers's difference algorithm to calculate the minimal number of updates to convert one list into another.
 *
 * It has [OnItemClickListener] to provide click listener for add to favorite image button.
 */
class TeamAdapter @Inject constructor() :
    ListAdapter<Team, TeamAdapter.TeamViewHolder>(TeamListDiffCallback()) {

    private var listener: OnItemClickListener? = null

    fun setOnItemClickedListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(
            // Use view binding
            TeamSearchResultRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val team = getItem(position)
        holder.bind(team)

        Picasso.get()
            .load(team.teamBadge)
            .apply {
                placeholder(R.drawable.ic_baseline_sports_soccer)
                into(holder.binding.circleImageViewTeamBadge)
            }

        setListeners(holder.binding, team)
    }

    private fun setListeners(binding: TeamSearchResultRowBinding, team: Team) {
        binding.apply {
            root.setOnClickListener {
                listener?.onTeamClicked(team)
            }
            imageViewFavorite.setOnClickListener { listener?.onFavoriteClicked(team) }
        }
    }

    class TeamViewHolder(val binding: TeamSearchResultRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(team: Team) {
            binding.apply {
                textViewTeamName.text = team.teamName
                textViewTeamDescription.text = team.description
                textViewSport.text = team.sport
                textViewLeague.text = team.league
                textViewCountry.text = team.country
                textViewStadium.text = team.stadium
            }
        }
    }
}

interface OnItemClickListener {
    fun onFavoriteClicked(team: Team)
    fun onTeamClicked(team: Team)
}

private class TeamListDiffCallback : DiffUtil.ItemCallback<Team>() {
    override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
        return oldItem == newItem
    }
}