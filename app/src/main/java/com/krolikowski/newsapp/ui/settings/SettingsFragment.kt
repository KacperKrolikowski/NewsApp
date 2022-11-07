package com.krolikowski.newsapp.ui.settings

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import com.krolikowski.domain.reposotories.SharedPreferenceRepository
import com.krolikowski.newsapp.R
import com.krolikowski.newsapp.base.BaseFragment
import com.krolikowski.newsapp.databinding.FragmentSettingsBinding
import com.krolikowski.newsapp.utils.views.NoFilterArrayAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment :
    BaseFragment<FragmentSettingsBinding, SettingsViewState, SettingsViewEvent, SettingsViewModel>(
        FragmentSettingsBinding::inflate
    ) {

    @Inject
    lateinit var sharedPreferenceRepository: SharedPreferenceRepository

    override val viewModel: SettingsViewModel by viewModels()

    override fun handleViewState(viewState: SettingsViewState) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setText()
        setSelectors()
    }

    private fun setText() {
        binding.apply {
            topNewsCountryTextView.text = getString(R.string.top_news_language)
            newsLanguageTextView.text = getString(R.string.news_language)
        }
    }

    private fun setSelectors() {
        binding.apply {
            Languages.topNewsCountry.map {
                getString(it.key) to it.value
            }.toMap().let {
                topNewsCountryDropdownText.initTopNewsCountrySelector(it)
            }

            Languages.newsLanguage.map {
                getString(it.key) to it.value
            }.toMap().let {
                newsLanguageDropdownText.initNewsLanguageSelector(it)
            }
        }
    }

    private fun AutoCompleteTextView.initTopNewsCountrySelector(languagesMap: Map<String, String>) {
        initAdapter(languagesMap.keys.toList())

        setText(
            languagesMap.filterValues { it == sharedPreferenceRepository.topNewsCountryCode }.keys.first(),
            false
        )

        setOnItemClickListener { adapterView, _, position, _ ->
            adapterView.getItemAtPosition(position).toString()
                .takeIf { languagesMap[it] != sharedPreferenceRepository.topNewsCountryCode }?.let {
                    sharedPreferenceRepository.topNewsCountryCode = languagesMap[it].toString()
                    this.setText(it, false)
                }
        }
    }

    private fun AutoCompleteTextView.initNewsLanguageSelector(languagesMap: Map<String, String>) {
        initAdapter(languagesMap.keys.toList())

        setText(
            languagesMap.filterValues { it == sharedPreferenceRepository.newsLanguageCode }.keys.first(),
            false
        )

        setOnItemClickListener { adapterView, _, position, _ ->
            adapterView.getItemAtPosition(position).toString()
                .takeIf { languagesMap[it] != sharedPreferenceRepository.newsLanguageCode }?.let {
                    sharedPreferenceRepository.newsLanguageCode = languagesMap[it].toString()
                    this.setText(it, false)
                }
        }
    }

    private fun AutoCompleteTextView.initAdapter(list: List<String>) {
        setAdapter(
            NoFilterArrayAdapter(
                context,
                R.layout.item_dropdown_list,
                list
            )
        )

        inputType = InputType.TYPE_NULL
        freezesText = false
    }
}