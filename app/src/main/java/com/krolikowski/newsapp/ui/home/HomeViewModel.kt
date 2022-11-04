package com.krolikowski.newsapp.ui.home

import androidx.lifecycle.viewModelScope
import com.krolikowski.domain.usecases.GetTopNewsUseCase
import com.krolikowski.newsapp.base.BaseViewModel
import com.krolikowski.newsapp.ui.groupie.items.NewsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTopNewsUseCase: GetTopNewsUseCase
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
                                news.title,
                                news.author,
                                news.description,
                                news.webUrl,
                                news.imageUrl,
                                news.date
                            )
                        })
                    )
                }
                .onFailure {

                }
        }
    }
}