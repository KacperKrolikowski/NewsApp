package com.krolikowski.newsapp.ui.more

import androidx.fragment.app.viewModels
import com.krolikowski.newsapp.base.BaseFragment
import com.krolikowski.newsapp.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment :
    BaseFragment<FragmentSettingsBinding, SettingsViewState, SettingsViewEvent, SettingsViewModel>(
        FragmentSettingsBinding::inflate
    ) {
    override val viewModel: SettingsViewModel by viewModels()

    override fun handleViewState(viewState: SettingsViewState) {
        TODO("Not yet implemented")
    }
}