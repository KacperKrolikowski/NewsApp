package com.krolikowski.newsapp.ui.webview

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.krolikowski.newsapp.base.BaseFragment
import com.krolikowski.newsapp.databinding.FragmentNewsviewerBinding

class NewsViewerFragment :
    BaseFragment<FragmentNewsviewerBinding, NewsViewerViewState, NewsViewerViewEvent, NewsViewerViewModel>(
        FragmentNewsviewerBinding::inflate
    ) {
    override val viewModel: NewsViewerViewModel by viewModels()
    private val navArgs: NewsViewerFragmentArgs by navArgs()

    override fun handleViewState(viewState: NewsViewerViewState) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(navArgs.webUrl)
            settings.apply {
                javaScriptEnabled = true
                setSupportZoom(true)
            }
        }
    }
}