package com.krolikowski.newsapp.ui.home

import androidx.fragment.app.viewModels
import com.krolikowski.newsapp.base.BaseFragment
import com.krolikowski.newsapp.databinding.FragmentHomeBinding

class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewState, HomeViewEvent, HomeViewModel>(
    FragmentHomeBinding::inflate
) {
    override val viewModel: HomeViewModel by viewModels()

    override fun handleViewState(viewState: HomeViewState) {

    }
}