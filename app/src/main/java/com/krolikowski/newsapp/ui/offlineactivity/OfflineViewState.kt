package com.krolikowski.newsapp.ui.offlineactivity

import com.krolikowski.newsapp.base.BaseViewState

sealed class OfflineViewState : BaseViewState {
    object GoBackToOnline : OfflineViewState()
}