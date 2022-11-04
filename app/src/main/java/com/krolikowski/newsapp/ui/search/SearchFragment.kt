package com.krolikowski.newsapp.ui.search

import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.krolikowski.newsapp.R
import com.krolikowski.newsapp.base.BaseFragment
import com.krolikowski.newsapp.databinding.FragmentSearchBinding
import com.krolikowski.newsapp.ui.home.NewsListAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewState, SearchViewEvent, SearchViewModel>(
    FragmentSearchBinding::inflate
) {
    override val viewModel: SearchViewModel by viewModels()

    private val pagingAdapter = NewsListAdapter(::navigateToWebView)

    private val pagingAdapterLoadStateListener: (CombinedLoadStates) -> Unit = { loadState ->
        when (loadState.source.refresh) {
            is LoadState.Loading -> changeLoadingState(true)
            is LoadState.Error -> {
                changeLoadingState()
                handleErrorState((loadState.source.refresh as LoadState.Error).error)
            }
            is LoadState.NotLoading -> {
                if (loadState.append.endOfPaginationReached) {
                    if (pagingAdapter.itemCount == 0) handleEmptyState()
                }
                changeLoadingState()
            }
            else -> Unit
        }
    }

    override fun handleViewState(viewState: SearchViewState) {
    }

    private fun navigateToWebView(url: String) {
        Log.d("DEBUG_", url)
    }

    private fun handleErrorState(error: Throwable) {
        Toast.makeText(context, getString(R.string.generic_error), Toast.LENGTH_SHORT).show()
        Timber.e(error)
    }

    private fun handleEmptyState() {
        Toast.makeText(context, getString(R.string.no_search_results), Toast.LENGTH_SHORT).show()
    }

    private fun changeLoadingState(isLoading: Boolean = false) {
        binding.loadingState.isVisible = isLoading
    }
}