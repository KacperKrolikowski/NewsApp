package com.krolikowski.newsapp.utils.extensions

import android.app.Activity
import android.content.Intent
import android.os.Bundle

inline fun <reified T : Activity> Activity.navigateTo(
    extras: Bundle = Bundle(),
    finishCurrent: Boolean = true
) {
    val intent = Intent(this, T::class.java)
    intent.putExtras(extras)
    startActivity(intent)
    if (finishCurrent) {
        finish()
    }
}