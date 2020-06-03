package com.zmeid.tracksportteam.view.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.zmeid.tracksportteam.databinding.FragmentTeamEventHistoryBinding
import com.zmeid.tracksportteam.view.adapter.TeamEventHistoryAdapter
import com.zmeid.tracksportteam.viewmodel.TeamEventHistoryViewModel
import timber.log.Timber
import javax.inject.Inject

class TeamEventHistoryFragment : BaseFragment(), View.OnClickListener {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    @Inject
    lateinit var teamEventHistoryAdapter: TeamEventHistoryAdapter

    private var _binding: FragmentTeamEventHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var teamEventHistoryViewModel: TeamEventHistoryViewModel
    private var teamId = 0

    private val args: TeamEventHistoryFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        teamEventHistoryViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(TeamEventHistoryViewModel::class.java)

        observeTeamHistory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTeamEventHistoryBinding.inflate(inflater, container, false)

        binding.apply {
            recyclerviewTeamEventHistory.layoutManager = LinearLayoutManager(context)
            recyclerviewTeamEventHistory.adapter = teamEventHistoryAdapter
        }

        binding.errorComponentHolder.buttonRetry.setOnClickListener(this)

        showBackNavigationButton()

        teamId = args.teamId
        getTeamHistory(teamId)
        return binding.root
    }

    private fun showBackNavigationButton() {
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
    }

    private fun getTeamHistory(teamId: Int) {
        teamEventHistoryViewModel.getTeamHistory(teamId)
    }

    private fun observeTeamHistory() {
        teamEventHistoryViewModel.teamEventHistoryResult.observe(this, Observer {
            Timber.d("TEAM EVENT HISTORY RESPONSE: \n $it")
            handleAPIResult(
                it,
                binding.errorComponentHolder.textViewUserMessage,
                binding.errorComponentHolder.buttonRetry,
                teamEventHistoryEventHistoryAdapter = teamEventHistoryAdapter
            )
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.errorComponentHolder.buttonRetry.id -> {
                getTeamHistory(teamId)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}