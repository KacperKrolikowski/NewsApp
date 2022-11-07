package com.krolikowski.newsapp.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import timber.log.Timber


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

fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}

fun Activity.hideSoftKeyboard() {
    try {
        safeLet(
            currentFocus?.windowToken,
            (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ) { token, imm ->
            imm.hideSoftInputFromWindow(token, 0)
        }
    } catch (e: Exception) {
        Timber.d(e, "Failed to hide soft keyboard")
    }
}

fun Activity.showSoftKeyboard(focusedView: View) {
    try {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.showSoftInput(
            focusedView,
            InputMethodManager.SHOW_IMPLICIT
        )
    } catch (e: Exception) {
        Timber.d(e, "Failed to show soft keyboard with focusedView: $focusedView")
    }
}

fun View.hideSoftKeyboard() {
    (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.hideSoftInputFromWindow(windowToken, 0)
}