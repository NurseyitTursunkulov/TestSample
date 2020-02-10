package com.example.testrussia.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testrussia.R
import com.example.testrussia.news.model.NewsModel
import kotlinx.android.synthetic.main.news_item.view.*

class NewsItemViewHolder (parent: ViewGroup) : RecyclerView.ViewHolder
    (LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)) {

    var news : NewsModel? = null

    fun bindTo(newsModel: NewsModel?){
        this.news = newsModel
        itemView.title.text = news?.title?.rendered
    }

}