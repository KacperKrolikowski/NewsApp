package com.krolikowski.newsapp.ui.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.krolikowski.domain.entities.NewsEntity
import com.krolikowski.domain.usecases.CheckIsNewsSavedUseCase
import com.krolikowski.domain.usecases.DeleteSavedNewsUseCase
import com.krolikowski.domain.usecases.GetNewsByQueryUseCase
import com.krolikowski.domain.usecases.SaveNewsUseCase
import com.krolikowski.newsapp.base.BaseViewModel
import com.krolikowski.newsapp.ui.groupie.items.NewsItem
import com.krolikowski.newsapp.ui.search.SearchFragment.Companion.MINIMAL_QUERY_LENGTH
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getNewsByQueryUseCase: GetNewsByQueryUseCase,
    private val checkIsNewsSavedUseCase: CheckIsNewsSavedUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase
) : BaseViewModel<SearchViewEvent, SearchViewState>() {
    private var searchJob: Job? = null

    override fun onViewEvent(viewEvent: SearchViewEvent) {
        when (viewEvent) {
            is SearchViewEvent.GetNews -> onSearchQueryChanged(viewEvent.query)
        }
    }

    private fun onSearchQueryChanged(query: String) {
        if (query.length >= MINIMAL_QUERY_LENGTH) {
            postLoadingState()
            searchDebounced(query)
        } else {
            mutableViewState.postValue(
                SearchViewState.Success(PagingData.empty())
            )
        }
    }

    private fun searchDebounced(searchText: String) {
        searchJob?.cancel()
        searchJob = searchText.takeIf { it.isNotEmpty() }?.let { text ->
            viewModelScope.launch {
                delay(DEBOUNCE_TIME)
                querySearch(text)
            }
        }
    }

    private fun querySearch(query: String) {
        viewModelScope.launch {
            getNewsByQuery(query).catch {
                postErrorState(it)
            }.collectLatest { paging ->
                mutableViewState.postValue(
                    SearchViewState.Success(
                        paging.map { news ->
                            NewsItem(
                                news,
                                viewModelScope,
                                checkIsNewsSavedUseCase,
                                saveNewsUseCase,
                                deleteSavedNewsUseCase
                            )
                        }
                    )
                )
            }
        }
    }

    private fun getNewsByQuery(query: String): Flow<PagingData<NewsEntity>> =
        getNewsByQueryUseCase.execute(query).cachedIn(viewModelScope)

    private fun postLoadingState() {
        mutableViewState.postValue(SearchViewState.Loading)
    }

    private fun postErrorState(throwable: Throwable) {
        mutableViewState.postValue(SearchViewState.Error(throwable))
    }

    companion object {
        private const val DEBOUNCE_TIME = 600L
    }
}