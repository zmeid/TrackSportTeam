package com.zmeid.tracksportteam.view.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.zmeid.tracksportteam.R
import com.zmeid.tracksportteam.databinding.FragmentSearchTeamBinding
import com.zmeid.tracksportteam.model.Team
import com.zmeid.tracksportteam.util.*
import com.zmeid.tracksportteam.view.adapter.OnItemClickListener
import com.zmeid.tracksportteam.view.adapter.TeamAdapter
import com.zmeid.tracksportteam.view.interfaces.SearchViewOnQueryTextChangedListener
import com.zmeid.tracksportteam.viewmodel.SearchTeamFragmentViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

private const val ALERT_DIALOG_IS_SHOWING_TAG = "alertDialogIsShowingTag"

class SearchTeamFragment : BaseFragment(), SearchViewOnQueryTextChangedListener,
    DialogUtils.ActivatePremiumClickedListener {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    @Inject
    lateinit var teamAdapter: TeamAdapter

    @Inject
    lateinit var layoutManager: LinearLayoutManager

    @Inject
    lateinit var errorMessageGenerator: ErrorMessageGenerator

    @Inject
    lateinit var dialogUtils: DialogUtils

    private lateinit var binding: FragmentSearchTeamBinding
    private lateinit var searchTeamFragmentViewModel: SearchTeamFragmentViewModel
    private lateinit var searchViewMenu: SearchView
    private lateinit var searchViewMenuItem: MenuItem
    private var searchQueryDelayJob: Job? = null
    private var wordToSearch: String by StringTrimDelegate()
    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        searchTeamFragmentViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(SearchTeamFragmentViewModel::class.java)

        observeTeamSearchResult()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchTeamBinding.inflate(inflater, container, false)

        binding.apply {
            recyclerviewTeamSearch.layoutManager = layoutManager
            recyclerviewTeamSearch.adapter = teamAdapter
        }
        setAdapterItemClickListener()

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
     * Observes if there are any changes in teamSearchResult live data and calls [handleTeamSearchResult]
     */
    private fun observeTeamSearchResult() {
        searchTeamFragmentViewModel.teamSearchResult.observe(this, Observer {
            Timber.d("TEAM SEARCH RESPONSE: \n $it")
            handleTeamSearchResult(it)
        })
    }

    /**
     * Handles team search result according to [ApiResponseWrapper]'s status.
     *
     * If [ApiResponseWrapper.Status.LOADING], it shows progress bar, hides user messages and hides retry button.
     * If [ApiResponseWrapper.Status.SUCCESS], updates [teamAdapter] with received data, hides progress bar, hides user messages and hides retry button. If the received data is empty; shows a message to user.
     * If [ApiResponseWrapper.Status.ERROR], it shows the error message and retry button. Clears recyclerview.
     */
    private fun handleTeamSearchResult(apiResponseWrapper: ApiResponseWrapper<List<Team>>) {
        when (apiResponseWrapper.status) {
            ApiResponseWrapper.Status.LOADING -> {
                showProgressBar(binding.progressBarTeamSearchFragment)
                hideUserMessageText(binding.textViewUserMessage)
                hideRetryButton(binding.buttonRetry)
            }
            ApiResponseWrapper.Status.SUCCESS -> {
                hideProgressBar(binding.progressBarTeamSearchFragment)
                hideUserMessageText(binding.textViewUserMessage)
                hideRetryButton(binding.buttonRetry)
                val teamList = apiResponseWrapper.data
                teamAdapter.submitList(teamList)
                if (teamList == null || teamList.isEmpty()) showUserMessage(
                    binding.textViewUserMessage,
                    getString(R.string.nothing_found)
                )
            }
            ApiResponseWrapper.Status.ERROR -> {
                hideProgressBar(binding.progressBarTeamSearchFragment)
                val errorMessage =
                    errorMessageGenerator.generateErrorMessage(apiResponseWrapper.exception!!)
                teamAdapter.submitList(null)
                showUserMessage(binding.textViewUserMessage, errorMessage)
                showRetryButton(binding.buttonRetry)
            }
        }
    }

    /**
     * Catches clicks on favorite button.
     */
    private fun setAdapterItemClickListener() {
        teamAdapter.setOnItemClickedListener(object : OnItemClickListener {
            override fun onFavoriteClicked(team: Team) {
                showAddToFavoriteAlertDialog()
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
        searchTeamFragmentViewModel.teamLastSearchWord.observeOnce(this, Observer {
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
                searchTeamFragmentViewModel.searchTeam(wordToSearch)
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
}