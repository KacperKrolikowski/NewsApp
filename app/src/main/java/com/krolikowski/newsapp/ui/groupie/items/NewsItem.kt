package com.krolikowski.newsapp.ui.groupie.items

import android.view.View
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import com.krolikowski.domain.entities.NewsEntity
import com.krolikowski.domain.usecases.CheckIsNewsSavedUseCase
import com.krolikowski.domain.usecases.DeleteSavedNewsUseCase
import com.krolikowski.domain.usecases.SaveNewsUseCase
import com.krolikowski.newsapp.R
import com.krolikowski.newsapp.databinding.ItemNewsBinding
import com.krolikowski.newsapp.utils.extensions.clear
import com.krolikowski.newsapp.utils.extensions.loadFromUrl
import com.krolikowski.newsapp.utils.extensions.setFormatDate
import com.xwray.groupie.viewbinding.BindableItem
import com.xwray.groupie.viewbinding.GroupieViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class NewsItem(
    val news: NewsEntity,
    val lifecycleScope: CoroutineScope,
    val checkIsNewsSavedUseCase: CheckIsNewsSavedUseCase,
    val saveNewsUseCase: SaveNewsUseCase,
    val deleteSavedNewsUseCase: DeleteSavedNewsUseCase,
    val updateOnItemDelete: (() -> Unit)? = null
) : BindableItem<ItemNewsBinding>() {

    override fun getLayout() = R.layout.item_news

    override fun initializeViewBinding(view: View) = ItemNewsBinding.bind(view)

    override fun bind(viewBinding: ItemNewsBinding, position: Int) {
        with(viewBinding) {
            with(news) {
                titleTextView.text = title
                shortDescriptionTextView.text = description
                authorTextView.text = author
                imageView.loadFromUrl(imageUrl)
                timeTextView.text = setFormatDate(date)
                bookmarkButton.apply {
                    lifecycleScope.launch {
                        updateState(checkIsNewsSavedUseCase.execute(webUrl))
                    }
                    setOnClickListener {
                        lifecycleScope.launch {
                            if (checkIsNewsSavedUseCase.execute(webUrl)) {
                                updateState(false)
                                deleteSavedNewsUseCase.execute(news)
                                updateOnItemDelete?.let { it() }
                            } else {
                                updateState(true)
                                saveNewsUseCase.execute(news)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun unbind(viewHolder: GroupieViewHolder<ItemNewsBinding>) {
        viewHolder.binding?.imageView?.clear()
        viewHolder.binding?.bookmarkButton?.clear()
        super.unbind(viewHolder)
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