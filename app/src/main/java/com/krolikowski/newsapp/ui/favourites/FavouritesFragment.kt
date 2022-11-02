package com.krolikowski.newsapp.ui.favourites

import androidx.fragment.app.viewModels
import com.krolikowski.newsapp.base.BaseFragment
import com.krolikowski.newsapp.databinding.FragmentFavouritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : BaseFragment<FragmentFavouritesBinding, FavouritesViewState, FavouritesViewEvent, FavouritesViewModel>(
    FragmentFavouritesBinding::inflate
) {
    override val viewModel: FavouritesViewModel by viewModels()

    override fun handleViewState(viewState: FavouritesViewState) {
        TODO("Not yet implemented")
    }
}