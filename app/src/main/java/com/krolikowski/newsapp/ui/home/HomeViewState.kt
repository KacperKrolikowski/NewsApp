package com.krolikowski.newsapp.ui.home

import com.krolikowski.newsapp.base.BaseViewState
import com.krolikowski.newsapp.ui.groupie.items.NewsItem

sealed class HomeViewState : BaseViewState {
    data class Success(
        val newsList: List<NewsItem>
    ) : HomeViewState()

    object Loading : HomeViewState()
}