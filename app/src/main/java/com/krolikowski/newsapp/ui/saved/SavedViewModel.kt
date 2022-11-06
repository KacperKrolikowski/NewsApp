package com.krolikowski.newsapp.ui.saved

import androidx.lifecycle.viewModelScope
import com.krolikowski.domain.usecases.CheckIsNewsSavedUseCase
import com.krolikowski.domain.usecases.DeleteSavedNewsUseCase
import com.krolikowski.domain.usecases.GetSavedNewsUseCase
import com.krolikowski.domain.usecases.SaveNewsUseCase
import com.krolikowski.newsapp.base.BaseViewModel
import com.krolikowski.newsapp.ui.groupie.items.NewsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val checkIsNewsSavedUseCase: CheckIsNewsSavedUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase
) : BaseViewModel<SavedViewEvent, SavedViewState>() {
    override fun onViewEvent(viewEvent: SavedViewEvent) {
        when (viewEvent) {
            is SavedViewEvent.GetSavedNews -> getSaved()
        }
    }

    private fun getSaved() {
        postLoadingState()
        viewModelScope.launch {
            getSavedNewsUseCase.execute().let {
                mutableViewState.postValue(SavedViewState.Success(it.map { news ->
                    NewsItem(
                        news,
                        viewModelScope,
                        checkIsNewsSavedUseCase,
                        saveNewsUseCase,
                        deleteSavedNewsUseCase
                    ) { getSaved() }
                }))
            }
        }
    }

    private fun postLoadingState() {
        mutableViewState.postValue(SavedViewState.Loading)
    }
}