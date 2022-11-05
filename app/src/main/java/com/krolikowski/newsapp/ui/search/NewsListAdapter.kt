package com.krolikowski.newsapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.krolikowski.domain.entities.NewsEntity
import com.krolikowski.newsapp.R
import com.krolikowski.newsapp.databinding.ItemNewsBinding
import com.krolikowski.newsapp.ui.groupie.items.NewsItem
import com.krolikowski.newsapp.utils.extensions.loadFromUrl
import com.krolikowski.newsapp.utils.extensions.setFormatDate
import kotlinx.coroutines.launch

class NewsListAdapter(
    private val itemClickListener: (webUrl: String) -> Unit
) : PagingDataAdapter<NewsItem, NewsListAdapter.NewsListItem>(
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
        fun bind(item: NewsItem) {
            with(binding) {
                with(item.news) {
                    titleTextView.text = title
                    shortDescriptionTextView.text = description
                    authorTextView.text = author
                    imageView.loadFromUrl(imageUrl)
                    timeTextView.text = setFormatDate(date)
                    bookmarkButton.apply {
                        item.lifecycleScope.launch {
                            updateState(item.checkIsNewsSavedUseCase.execute(webUrl))
                        }
                        setOnClickListener {
                            item.lifecycleScope.launch {
                                if (item.checkIsNewsSavedUseCase.execute(webUrl)) {
                                    updateState(false)
                                    item.deleteSavedNewsUseCase.execute(item.news)
                                } else {
                                    updateState(true)
                                    item.saveNewsUseCase.execute(item.news)
                                }
                            }
                        }
                    }
                }
                root.setOnClickListener {
                    itemClickListener(item.news.webUrl)
                }
            }
        }

        private fun ImageView.updateState(isNewsSaved: Boolean) {
            apply {
                this.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context,
                        if (isNewsSaved) R.drawable.ic_bookmark_added else R.drawable.ic_bookmark_add
                    )
                )
            }
        }
    }

    object NewsComparator : DiffUtil.ItemCallback<NewsItem>() {
        override fun areItemsTheSame(
            oldItem: NewsItem,
            newItem: NewsItem
        ) = oldItem.news.webUrl == newItem.news.webUrl


        override fun areContentsTheSame(
            oldItem: NewsItem,
            newItem: NewsItem
        ) = oldItem == newItem
    }
}