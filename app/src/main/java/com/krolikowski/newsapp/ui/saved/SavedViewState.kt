package com.krolikowski.newsapp.ui.saved

import com.krolikowski.newsapp.base.BaseViewState
import com.krolikowski.newsapp.ui.groupie.items.NewsItem

sealed class SavedViewState : BaseViewState {
    data class Success(val newsList: List<NewsItem>) : SavedViewState()
    object Loading : SavedViewState()
}