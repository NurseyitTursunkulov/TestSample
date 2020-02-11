package com.example.testrussia.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testrussia.databinding.NewsItemBinding
import com.example.testrussia.news.model.NewsModel

class NewsAdapter(private val viewModel: NewsViewModel) :  ListAdapter<NewsModel, NewsAdapter.ViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: NewsItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: NewsViewModel, newsModel: NewsModel) {

            binding.viewmodel = viewModel
            binding.newsItem = newsModel
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NewsItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<NewsModel>() {
            override fun areItemsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean =
                oldItem == newItem
        }
    }
}