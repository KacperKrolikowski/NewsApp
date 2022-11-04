package com.krolikowski.newsapp.utils.extensions

import android.os.SystemClock
import android.view.View
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.OnItemClickListener

fun GroupieAdapter.setOnItemClickDebounce(
    debounceTime: Long = 600L,
    action: (item: Item<*>, view: View) -> Unit
) {
    this.setOnItemClickListener(object : OnItemClickListener {
        private var lastClickTime: Long = 0

        override fun onItemClick(item: Item<*>, view: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action(item, view)

            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}