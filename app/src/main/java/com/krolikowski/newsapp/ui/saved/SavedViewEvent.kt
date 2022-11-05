package com.krolikowski.newsapp.ui.saved

import com.krolikowski.newsapp.base.BaseViewEvent

sealed class SavedViewEvent : BaseViewEvent {
    object GetSavedNews : SavedViewEvent()
}