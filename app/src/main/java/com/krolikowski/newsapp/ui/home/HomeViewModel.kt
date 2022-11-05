package com.krolikowski.newsapp.ui.home

import androidx.lifecycle.viewModelScope
import com.krolikowski.domain.usecases.CheckIsNewsSavedUseCase
import com.krolikowski.domain.usecases.DeleteSavedNewsUseCase
import com.krolikowski.domain.usecases.GetTopNewsUseCase
import com.krolikowski.domain.usecases.SaveNewsUseCase
import com.krolikowski.newsapp.base.BaseViewModel
import com.krolikowski.newsapp.ui.groupie.items.NewsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTopNewsUseCase: GetTopNewsUseCase,
    private val checkIsNewsSavedUseCase: CheckIsNewsSavedUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase
) : BaseViewModel<HomeViewEvent, HomeViewState>() {

    init {
        postLoadingState()
    }

    override fun onViewEvent(viewEvent: HomeViewEvent) {
        when (viewEvent) {
            HomeViewEvent.GetNews -> getNews()
        }
    }

    private fun postLoadingState() {
        mutableViewState.postValue(
            HomeViewState.Loading
        )
    }

    private fun getNews() {
        viewModelScope.launch {
            getTopNewsUseCase.execute()
                .onSuccess {
                    mutableViewState.postValue(
                        HomeViewState.Success(it.newsList.map { news ->
                            NewsItem(
                                news,
                                viewModelScope,
                                checkIsNewsSavedUseCase,
                                saveNewsUseCase,
                                deleteSavedNewsUseCase
                            )
                        })
                    )
                }
                .onFailure {
                    postErrorState(it)
                }
        }
    }

    private fun postErrorState(throwable: Throwable) {
        Timber.e(throwable)
        mutableViewState.postValue(HomeViewState.Error)
    }
}