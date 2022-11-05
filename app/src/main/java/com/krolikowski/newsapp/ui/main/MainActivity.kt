package com.krolikowski.newsapp.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.krolikowski.newsapp.R
import com.krolikowski.newsapp.base.BaseActivity
import com.krolikowski.newsapp.databinding.ActivityMainBinding
import com.krolikowski.newsapp.ui.offlineactivity.OfflineActivity
import com.krolikowski.newsapp.utils.extensions.navigateTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewState, MainViewEvent, MainViewModel>() {

    override val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setNavigation()
    }

    override fun handleViewState(viewState: MainViewState) {
        when (viewState) {
            is MainViewState.NavigateToOffline -> navigateTo<OfflineActivity>()
        }
    }

    private fun setNavigation(){
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_saved -> {
                    navView.isVisible = true
                }
                else -> navView.isVisible = false
            }
        }
    }
}