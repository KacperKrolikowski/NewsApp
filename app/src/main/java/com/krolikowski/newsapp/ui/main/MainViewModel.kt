package com.krolikowski.newsapp.ui.main

import androidx.lifecycle.viewModelScope
import com.krolikowski.newsapp.base.BaseViewModel
import com.krolikowski.newsapp.networking.ConnectivityHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val connectivityHelper: ConnectivityHelper
) : BaseViewModel<MainViewEvent, MainViewState>() {

    init {
        listenOnConnectionChange()
    }

    override fun onViewEvent(viewEvent: MainViewEvent) {}

    private fun listenOnConnectionChange() {
        viewModelScope.launch {
            connectivityHelper.connectionState.collectLatest {
                if (it == ConnectivityHelper.ConnectivityState.NOT_CONNECTED) {
                    mutableViewState.postValue(MainViewState.NavigateToOffline)
                }
            }
        }
    }
}