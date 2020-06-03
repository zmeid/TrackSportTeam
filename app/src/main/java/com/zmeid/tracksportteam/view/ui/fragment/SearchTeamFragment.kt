package com.zmeid.tracksportteam.view.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zmeid.tracksportteam.R
import com.zmeid.tracksportteam.databinding.FragmentSearchTeamBinding
import com.zmeid.tracksportteam.model.Team
import com.zmeid.tracksportteam.util.DialogUtils
import com.zmeid.tracksportteam.util.StringTrimDelegate
import com.zmeid.tracksportteam.util.observeOnce
import com.zmeid.tracksportteam.view.adapter.OnItemClickListener
import com.zmeid.tracksportteam.view.adapter.TeamAdapter
import com.zmeid.tracksportteam.view.interfaces.SearchViewOnQueryTextChangedListener
import com.zmeid.tracksportteam.viewmodel.SearchTeamViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

private const val ALERT_DIALOG_IS_SHOWING_TAG = "alertDialogIsShowingTag"

class SearchTeamFragment : BaseFragment(), SearchViewOnQueryTextChangedListener,
    DialogUtils.ActivatePremiumClickedListener, View.OnClickListener {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    @Inject
    lateinit var teamAdapter: TeamAdapter

    @Inject
    lateinit var dialogUtils: DialogUtils

    private lateinit var binding: FragmentSearchTeamBinding
    private lateinit var searchTeamViewModel: SearchTeamViewModel
    private lateinit var searchViewMenu: SearchView
    private lateinit var searchViewMenuItem: MenuItem
    private var searchQueryDelayJob: Job? = null
    private var wordToSearch: String by StringTrimDelegate()
    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        searchTeamViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(SearchTeamViewModel::class.java)

        observeTeamSearchResult()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchTeamBinding.inflate(inflater, container, false)

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(false)

        binding.apply {
            recyclerviewTeamSearch.layoutManager = LinearLayoutManager(context)
            recyclerviewTeamSearch.adapter = teamAdapter
        }

        setAdapterItemClickListener()

        binding.errorComponentHolder.buttonRetry.setOnClickListener(this)

        if (teamAdapter.itemCount > 0) {
            hideUserMessageText(binding.errorComponentHolder.textViewUserMessage)
        } else {
            showUserMessage(
                binding.errorComponentHolder.textViewUserMessage,
                getString(R.string.type_to_search_team)
            )
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        alertDialog?.isShowing?.let {
            outState.putBoolean(ALERT_DIALOG_IS_SHOWING_TAG, it)
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        val isAlertDialogShowing =
            savedInstanceState?.getBoolean(ALERT_DIALOG_IS_SHOWING_TAG, false)
        if (isAlertDialogShowing == true) showAddToFavoriteAlertDialog()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * Observes if there are any changes in teamSearchResult live data and calls [handleAPIResult]
     */
    private fun observeTeamSearchResult() {
        searchTeamViewModel.teamSearchResult.observe(this, Observer {
            Timber.d("TEAM SEARCH RESPONSE: \n $it")
            handleAPIResult(
                it,
                binding.errorComponentHolder.textViewUserMessage,
                binding.errorComponentHolder.buttonRetry,
                teamAdapter
            )
        })
    }

    /**
     * Catches clicks on favorite button and team rows.
     */
    private fun setAdapterItemClickListener() {
        teamAdapter.setOnItemClickedListener(object : OnItemClickListener {
            override fun onFavoriteClicked(team: Team) {
                showAddToFavoriteAlertDialog()
            }

            override fun onTeamClicked(team: Team) {
                val action =
                    SearchTeamFragmentDirections.actionSearchTeamFragmentToTeamEventHistoryFragment(
                        team.id
                    )
                view?.findNavController()?.navigate(action)
            }
        })
    }

    /**
     * Initializes search menu and sets [SearchViewOnQueryTextChangedListener].
     */
    override fun onPrepareOptionsMenu(menu: Menu) {
        searchViewMenuItem = menu.findItem(R.id.menu_search)
        searchViewMenu = searchViewMenuItem.actionView as SearchView
        searchViewMenu.maxWidth =
            Integer.MAX_VALUE // This is a hack to solve close button wrong alignment.
        observeLastSearchWord()
        searchViewMenu.setOnQueryTextListener(this)
    }

    /**
     * If search view is not empty and the devices is rotated; it observes once to get last searched word.
     */
    private fun observeLastSearchWord() {
        searchTeamViewModel.teamLastSearchWord.observeOnce(this, Observer {
            searchViewMenuItem.expandActionView()
            searchViewMenu.setQuery(it, false)
            wordToSearch = it
        })
    }

    override fun onQueryTextChange(word: String): Boolean {
        searchQueryDelayJob?.cancel()
        searchQueryDelayJob = lifecycleScope.launch {
            delay(700)
            if (word.isNotBlank()) {
                wordToSearch = word
                searchTeamViewModel.searchTeam(wordToSearch, false)
            }
        }
        return true
    }

    override fun activatePremiumClicked() {
        Toast.makeText(context, context?.getString(R.string.not_ready_feature), Toast.LENGTH_SHORT)
            .show()
    }

    private fun showAddToFavoriteAlertDialog() {
        alertDialog = dialogUtils.createAddToFavoriteDialog()
        alertDialog?.show()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.errorComponentHolder.buttonRetry.id -> {
                searchTeamViewModel.searchTeam(wordToSearch, true)
            }
        }
    }
}