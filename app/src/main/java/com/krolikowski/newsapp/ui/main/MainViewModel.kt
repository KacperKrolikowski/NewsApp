package com.krolikowski.newsapp.ui.main

import com.krolikowski.newsapp.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

) : BaseViewModel<MainViewEvent, MainViewState>() {
    override fun onViewEvent(viewEvent: MainViewEvent) {

    }
}