package com.krolikowski.newsapp.networking

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectivityHelper @Inject constructor(@ApplicationContext val context: Context) {

    private val mutableConnectionState: MutableStateFlow<ConnectivityState> =
        MutableStateFlow(ConnectivityState.UNKNOWN)
    val connectionState: StateFlow<ConnectivityState> = mutableConnectionState

    fun init() {
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)?.let {
            if (it.activeNetwork == null) {
                mutableConnectionState.value =
                    ConnectivityState.NOT_CONNECTED
            }
            it.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    Timber.d("connectivity available")
                    mutableConnectionState.value = ConnectivityState.CONNECTED
                }

                override fun onLost(network: Network) {
                    Timber.d("connectivity lost")
                    mutableConnectionState.value = ConnectivityState.NOT_CONNECTED
                }
            })
        }
    }

    fun hasInternetConnection(): Boolean = connectionState.value == ConnectivityState.CONNECTED

    enum class ConnectivityState {
        CONNECTED, NOT_CONNECTED, UNKNOWN
    }
}