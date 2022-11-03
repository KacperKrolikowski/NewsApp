package com.krolikowski.newsapp.ui.offlineactivity

import androidx.lifecycle.viewModelScope
import com.krolikowski.newsapp.base.BaseViewModel
import com.krolikowski.newsapp.networking.ConnectivityHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfflineViewModel @Inject constructor(
    private val connectivityHelper: ConnectivityHelper
) : BaseViewModel<OfflineViewEvent, OfflineViewState>() {
    init {
        listenOnConnectionChange()
    }

    override fun onViewEvent(viewEvent: OfflineViewEvent) {}

    private fun listenOnConnectionChange() {
        viewModelScope.launch {
            connectivityHelper.connectionState.collectLatest {
                if (it == ConnectivityHelper.ConnectivityState.CONNECTED) {
                    mutableViewState.postValue(OfflineViewState.GoBackToOnline)
                }
            }
        }
    }
}