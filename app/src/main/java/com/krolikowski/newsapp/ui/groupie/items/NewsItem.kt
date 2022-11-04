package com.krolikowski.newsapp.ui.groupie.items

import android.text.format.DateUtils
import android.view.View
import com.krolikowski.domain.entities.NewsEntity
import com.krolikowski.newsapp.R
import com.krolikowski.newsapp.databinding.ItemNewsBinding
import com.krolikowski.newsapp.utils.extensions.clear
import com.krolikowski.newsapp.utils.extensions.loadFromUrl
import com.xwray.groupie.viewbinding.BindableItem
import com.xwray.groupie.viewbinding.GroupieViewHolder
import java.text.SimpleDateFormat
import java.util.*

data class NewsItem(
    private val title: String,
    private val author: String,
    private val description: String,
    val webUrl: String,
    private val imageUrl: String,
    private val date: String
) : BindableItem<ItemNewsBinding>() {

    override fun getLayout() = R.layout.item_news

    override fun initializeViewBinding(view: View) = ItemNewsBinding.bind(view)

    override fun bind(viewBinding: ItemNewsBinding, position: Int) {
        with(viewBinding) {
            titleTextView.text = title
            shortDescriptionTextView.text = description
            authorTextView.text = author
            imageView.loadFromUrl(imageUrl)
            timeTextView.text = formatDate()
        }
    }

    override fun unbind(viewHolder: GroupieViewHolder<ItemNewsBinding>) {
        viewHolder.binding?.imageView?.clear()
        viewHolder.binding?.bookmarkButton?.clear()
        super.unbind(viewHolder)
    }

    private fun formatDate(): String {
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
}