package com.krolikowski.newsapp.ui.groupie.shimmer

import android.view.View
import com.krolikowski.newsapp.R
import com.krolikowski.newsapp.databinding.ItemNewsShimmerBinding
import com.xwray.groupie.viewbinding.BindableItem
import com.xwray.groupie.viewbinding.GroupieViewHolder

class ShimmerItem : BindableItem<ItemNewsShimmerBinding>() {
    override fun getLayout() = R.layout.item_news_shimmer
    override fun initializeViewBinding(view: View) = ItemNewsShimmerBinding.bind(view)
    override fun bind(binding: ItemNewsShimmerBinding, position: Int) {
        binding.shimmerLayout.startShimmer()
    }

    override fun unbind(viewHolder: GroupieViewHolder<ItemNewsShimmerBinding>) {
        viewHolder.binding.shimmerLayout.stopShimmer()
        super.unbind(viewHolder)
    }
}