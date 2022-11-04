package com.krolikowski.newsapp.utils.extensions

import android.os.SystemClock
import android.text.format.DateUtils
import android.view.View
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.OnItemClickListener
import java.text.SimpleDateFormat

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

fun setFormatDate(date: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val time = inputFormat.parse(date)
    return time?.let {
        DateUtils.getRelativeTimeSpanString(
            time.time,
            System.currentTimeMillis(),
            DateUtils.MINUTE_IN_MILLIS
        ) as String
    } ?: ""
}