package com.krolikowski.newsapp.ui.search

import com.krolikowski.newsapp.base.BaseViewEvent

sealed class SearchViewEvent : BaseViewEvent {
    data class GetNews(val query: String) : SearchViewEvent()
}