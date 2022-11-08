package com.krolikowski.newsapp.ui.search

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.krolikowski.newsapp.R
import com.krolikowski.newsapp.base.BaseFragment
import com.krolikowski.newsapp.databinding.FragmentSearchBinding
import com.krolikowski.newsapp.ui.groupie.items.NewsItem
import com.krolikowski.newsapp.utils.extensions.hideSoftKeyboard
import com.krolikowski.newsapp.utils.views.ClearFocusEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SearchFragment :
    BaseFragment<FragmentSearchBinding, SearchViewState, SearchViewEvent, SearchViewModel>(
        FragmentSearchBinding::inflate
    ) {
    override val viewModel: SearchViewModel by viewModels()

    private val pagingAdapter = NewsListAdapter(::navigateToWebView)
    private var currentQuery: String = ""

    private val searchTextWatcher = SearchTextWatcher()

    private val pagingAdapterLoadStateListener: (CombinedLoadStates) -> Unit = { loadState ->
        when (loadState.source.refresh) {
            is LoadState.Loading -> changeLoadingState(true)
            is LoadState.Error -> {
                changeLoadingState()
                handleErrorState((loadState.source.refresh as LoadState.Error).error)
            }
            is LoadState.NotLoading -> {
                if (loadState.append.endOfPaginationReached) {
                    if (pagingAdapter.itemCount == 0 && currentQuery.length >= MINIMAL_QUERY_LENGTH) handleEmptyState()
                }
                changeLoadingState()
            }
            else -> Unit
        }
    }

    override fun handleViewState(viewState: SearchViewState) {
        when (viewState) {
            is SearchViewState.Success -> {
                changeLoadingState(false)
                handleSuccessState(
                    viewState.newsPaging
                )
            }

            SearchViewState.Loading -> {
                changeLoadingState(true)
            }

            is SearchViewState.Error -> {
                changeLoadingState()
                handleErrorState(viewState.error)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAdapterStateListener()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            searchTextField.initSearchTextLayout()
            searchEditText.initSearchTextInput()
            recyclerView.initRecyclerView()
            root.initFocusControl()
        }
    }

    override fun onResume() {
        super.onResume()
        changeLoadingState()
    }

    override fun onDestroyView() {
        binding.apply {
            searchEditText.removeTextChangedListener(searchTextWatcher)
            recyclerView.adapter = null
        }

        pagingAdapter.removeLoadStateListener(pagingAdapterLoadStateListener)

        super.onDestroyView()
    }

    private fun handleSuccessState(
        news: PagingData<NewsItem>
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            pagingAdapter.submitData(news)
        }
    }

    private fun RecyclerView.initRecyclerView() {
        this.apply {
            setHasFixedSize(true)
            adapter = pagingAdapter
            onFlingListener = null
            setOnTouchListener { _, _ ->
                performClick()
                requireActivity().hideSoftKeyboard()
                clearFocusOnSearchEditText()
                false
            }
        }
    }

    private fun setAdapterStateListener() {
        pagingAdapter.addLoadStateListener(pagingAdapterLoadStateListener)
        scrollToTopOnRefresh()
    }

    private fun scrollToTopOnRefresh() = lifecycleScope.launchWhenCreated {
        pagingAdapter.loadStateFlow
            .distinctUntilChangedBy { it.refresh }
            .filter { it.refresh is LoadState.NotLoading }
            .collect { binding.recyclerView.scrollToPosition(0) }
    }

    private fun navigateToWebView(url: String) {
        val navigateToNewsViewer =
            SearchFragmentDirections.actionNavigationSearchToNewsViewerFragment(url)
        findNavController().navigate(navigateToNewsViewer)
    }

    private fun handleErrorState(error: Throwable) {
        Toast.makeText(context, getString(R.string.generic_error), Toast.LENGTH_SHORT).show()
        Timber.e(error)
    }

    private fun handleEmptyState() {
        setEmptyList()
        Toast.makeText(context, getString(R.string.no_search_results), Toast.LENGTH_SHORT).show()
    }

    private fun setEmptyList() {
        lifecycleScope.launch { pagingAdapter.submitData(PagingData.empty()) }
    }

    private fun changeLoadingState(isLoading: Boolean = false) {
        binding.loadingState.isVisible = isLoading
    }

    private fun TextInputLayout.initSearchTextLayout() {
        setEndIconTintList(
            ColorStateList.valueOf(
                Color.BLACK
            )
        )
        setEndIconOnClickListener {
            viewModel.onViewEvent(
                SearchViewEvent.GetNews("")
            )

            binding.searchEditText.text?.clear()
        }
        startIconDrawable =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_search)
        setStartIconTintList(
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.gray
                )
            )
        )
    }

    private fun ClearFocusEditText.initSearchTextInput() {
        setTextColor(Color.BLACK)
        addTextChangedListener(searchTextWatcher)
        hint = getString(R.string.search)
        setHintTextColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.gray)))
    }

    private fun View.initFocusControl() {
        this.setOnTouchListener { _, _ ->
            performClick()
            requireActivity().hideSoftKeyboard()
            clearFocusOnSearchEditText()
            false
        }
    }

    private fun clearFocusOnSearchEditText() {
        binding.searchEditText.clearFocus()
    }

    private inner class SearchTextWatcher : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(editable: Editable?) {
            editable?.let { text ->
                when {
                    text.isEmpty() -> {
                        setEmptyList()
                    }
                    text.isNotEmpty() -> {
                        when {
                            (text.length >= MINIMAL_QUERY_LENGTH && text.toString() == currentQuery) -> Unit
                            (text.length >= MINIMAL_QUERY_LENGTH && currentQuery.length >= 2) -> {
                                changeLoadingState(true)
                                setEmptyList()
                                viewModel.onViewEvent(
                                    SearchViewEvent.GetNews(text.toString())
                                )
                            }

                            (text.length > 1 && currentQuery.isEmpty()) -> {
                                currentQuery = ""
                                binding.searchEditText.text?.clear()
                                viewModel.onViewEvent(SearchViewEvent.GetNews(""))
                            }

                            else -> viewModel.onViewEvent(SearchViewEvent.GetNews(""))
                        }
                    }
                    else -> Unit
                }
                currentQuery = text.toString()
            }
        }
    }

    companion object {
        const val MINIMAL_QUERY_LENGTH = 3
    }
}