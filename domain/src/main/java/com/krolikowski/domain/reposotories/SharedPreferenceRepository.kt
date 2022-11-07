package com.krolikowski.domain.reposotories

interface SharedPreferenceRepository {
    var appLanguageCode: String
    var topNewsCountryCode: String
    var newsLanguageCode: String
}