package com.example.testrussia.data

import com.example.testrussia.news.model.NewsModel

interface NewsRepository {
    suspend fun getNews(forceUpdate: Boolean):Result<List<NewsModel>>
}