package com.krolikowski.newsapp.ui.offlineactivity

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.viewModels
import com.krolikowski.newsapp.R
import com.krolikowski.newsapp.base.BaseActivity
import com.krolikowski.newsapp.databinding.ActivityOfflineBinding
import com.krolikowski.newsapp.ui.main.MainActivity
import com.krolikowski.newsapp.utils.extensions.navigateTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OfflineActivity :
    BaseActivity<ActivityOfflineBinding, OfflineViewState, OfflineViewEvent, OfflineViewModel>() {

    override val binding by lazy { ActivityOfflineBinding.inflate(layoutInflater) }
    override val viewModel: OfflineViewModel by viewModels()

    override fun handleViewState(viewState: OfflineViewState) {
        when (viewState) {
            OfflineViewState.GoBackToOnline -> navigateTo<MainActivity>()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.noConnectionText.text = getString(R.string.offline_text)
    }
}
