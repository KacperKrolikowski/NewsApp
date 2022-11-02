package com.krolikowski.newsapp.ui.home

import com.krolikowski.newsapp.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : BaseViewModel<HomeViewEvent, HomeViewState>() {
    override fun onViewEvent(viewEvent: HomeViewEvent) {

    }
}