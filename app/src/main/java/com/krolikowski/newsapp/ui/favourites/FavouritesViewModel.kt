package com.krolikowski.newsapp.ui.favourites

import com.krolikowski.newsapp.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(

) : BaseViewModel<FavouritesViewEvent, FavouritesViewState>() {
    override fun onViewEvent(viewEvent: FavouritesViewEvent) {

    }
}