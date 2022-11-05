package com.krolikowski.newsapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.krolikowski.newsapp.R
import com.krolikowski.newsapp.base.BaseFragment
import com.krolikowski.newsapp.databinding.FragmentHomeBinding
import com.krolikowski.newsapp.ui.groupie.items.NewsItem
import com.krolikowski.newsapp.ui.groupie.shimmer.ShimmerItem
import com.krolikowski.newsapp.utils.extensions.setOnItemClickDebounce
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewState, HomeViewEvent, HomeViewModel>(
    FragmentHomeBinding::inflate
) {
    override val viewModel: HomeViewModel by viewModels()

    private val contentAdapter = GroupieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onViewEvent(HomeViewEvent.GetNews)
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

    override fun handleViewState(viewState: HomeViewState) {
        when (viewState) {
            HomeViewState.Loading -> setLoadingState()
            is HomeViewState.Success -> setContent(viewState.newsList)
            HomeViewState.Error -> showErrorMessage()
        }
    }

    override fun onDestroyView() {
        binding.contentContainer.adapter = null
        super.onDestroyView()
    }

    private fun navigateToWebView(url: String) {
        Log.d("DEBUG_", url)
    }

    private fun setLoadingState() {
        contentAdapter.update(List(10) { ShimmerItem() })
        setRecyclerViewScrollable(false)
    }

    private fun setContent(newsList: List<NewsItem>) {
        setRecyclerViewScrollable(true)
        contentAdapter.update(newsList)
    }

    private fun setRecyclerViewScrollable(isScrollable: Boolean) {
        binding.contentContainer.layoutManager = object : LinearLayoutManager(requireContext()) {
            override fun canScrollVertically(): Boolean {
                return isScrollable
            }

            override fun setOrientation(orientation: Int) {
                super.setOrientation(VERTICAL)
            }
        }
    }

    private fun showErrorMessage() {
        Toast.makeText(context, getString(R.string.generic_error), Toast.LENGTH_SHORT).show()
    }
}