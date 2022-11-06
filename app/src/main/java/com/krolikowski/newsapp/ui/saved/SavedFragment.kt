package com.krolikowski.newsapp.ui.saved

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.krolikowski.newsapp.R
import com.krolikowski.newsapp.base.BaseFragment
import com.krolikowski.newsapp.databinding.FragmentSavedBinding
import com.krolikowski.newsapp.ui.groupie.items.NewsItem
import com.krolikowski.newsapp.utils.extensions.setOnItemClickDebounce
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SavedFragment :
    BaseFragment<FragmentSavedBinding, SavedViewState, SavedViewEvent, SavedViewModel>(
        FragmentSavedBinding::inflate
    ) {
    override val viewModel: SavedViewModel by viewModels()

    private val contentAdapter = GroupieAdapter().apply {
        setHasStableIds(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onViewEvent(SavedViewEvent.GetSavedNews)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.contentContainer.apply {
            adapter = contentAdapter
        }
        contentAdapter.setOnItemClickDebounce { item, _ ->
            if (item is NewsItem) item.news.webUrl.let(::navigateToWebView)
        }
    }

    override fun onDestroyView() {
        binding.contentContainer.adapter = null
        super.onDestroyView()
    }

    override fun handleViewState(viewState: SavedViewState) {
        when (viewState) {
            is SavedViewState.Success -> setContent(viewState.newsList)
            SavedViewState.Loading -> setLoadingState(true)
        }
    }

    private fun navigateToWebView(url: String) {
        val navigateToNewsViewer =
            SavedFragmentDirections.actionNavigationSavedToNewsViewerFragment(url)
        findNavController().navigate(navigateToNewsViewer)
    }

    private fun setContent(newsList: List<NewsItem>) {
        setLoadingState(false)
        if (newsList.isNotEmpty()) contentAdapter.update(newsList) else setEmptyState()
    }

    private fun setEmptyState() {
        binding.apply {
            emptyText.apply {
                text = getString(R.string.no_saved)
                isVisible = true
            }
            contentContainer.isVisible = false
            animationView.apply {
                isVisible = true
                playAnimation()
            }
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        binding.loadingState.isVisible = isLoading
    }
}