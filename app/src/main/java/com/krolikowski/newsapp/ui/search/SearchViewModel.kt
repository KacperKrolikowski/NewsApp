package com.krolikowski.newsapp.ui.search

import com.krolikowski.newsapp.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(

) : BaseViewModel<SearchViewEvent, SearchViewState>() {
    override fun onViewEvent(viewEvent: SearchViewEvent) {

    }
}