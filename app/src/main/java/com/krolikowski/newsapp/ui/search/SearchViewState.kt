package com.krolikowski.newsapp.ui.search

import androidx.paging.PagingData
import com.krolikowski.domain.entities.NewsEntity
import com.krolikowski.newsapp.base.BaseViewState

sealed class SearchViewState : BaseViewState {
    data class Success(
        val newsPaging: PagingData<NewsEntity>
    ) : SearchViewState()

    object Loading : SearchViewState()

    data class Error(val error: Throwable) : SearchViewState()
}