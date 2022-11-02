package com.krolikowski.newsapp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import timber.log.Timber

abstract class BaseActivity<VB : ViewBinding, VS : BaseViewState, VE : BaseViewEvent, VM : BaseViewModel<VE, VS>> : AppCompatActivity() {

    protected abstract val binding: VB
    protected abstract val viewModel: VM

    abstract fun handleViewState(viewState: VS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.v("onCreate ${javaClass.simpleName}")
        setContentView(binding.root)
        viewModel.viewState.observe(this, ::handleViewState)
    }
}