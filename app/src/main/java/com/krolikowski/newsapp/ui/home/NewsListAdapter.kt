package com.krolikowski.newsapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.krolikowski.domain.entities.NewsEntity
import com.krolikowski.newsapp.databinding.ItemNewsBinding
import com.krolikowski.newsapp.utils.extensions.loadFromUrl
import com.krolikowski.newsapp.utils.extensions.setFormatDate

class NewsListAdapter(
    private val itemClickListener: (webUrl: String) -> Unit
) : PagingDataAdapter<NewsEntity, NewsListAdapter.NewsListItem>(
    NewsComparator
) {
    override fun onBindViewHolder(holder: NewsListItem, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListItem {
        return NewsListItem(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class NewsListItem(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewsEntity) {
            with(binding) {
                titleTextView.text = item.title
                shortDescriptionTextView.text = item.description
                authorTextView.text = item.author
                imageView.loadFromUrl(item.imageUrl)
                timeTextView.text = setFormatDate(item.date)
            }
        }

    }

    object NewsComparator : DiffUtil.ItemCallback<NewsEntity>() {
        override fun areItemsTheSame(
            oldItem: NewsEntity,
            newItem: NewsEntity
        ) = oldItem.webUrl == newItem.webUrl


        override fun areContentsTheSame(
            oldItem: NewsEntity,
            newItem: NewsEntity
        ) = oldItem == newItem
    }
}