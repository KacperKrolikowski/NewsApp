package com.krolikowski.newsapp.ui.search

import androidx.fragment.app.viewModels
import com.krolikowski.newsapp.base.BaseFragment
import com.krolikowski.newsapp.databinding.FragmentSearchBinding

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewState, SearchViewEvent, SearchViewModel>(
    FragmentSearchBinding::inflate
) {
    override val viewModel: SearchViewModel by viewModels()

    override fun handleViewState(viewState: SearchViewState) {
    }
}