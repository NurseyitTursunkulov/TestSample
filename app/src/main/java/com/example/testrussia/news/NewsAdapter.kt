package com.example.testrussia.news

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.testrussia.news.model.NewsModel

class NewsAdapter : PagedListAdapter<NewsModel, NewsItemViewHolder>(diffCallback) {
    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder =
        NewsItemViewHolder(parent)

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<NewsModel>() {
            override fun areItemsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean =
                oldItem == newItem
        }
    }
}