package com.example.testrussia.data

import com.example.testrussia.news.model.NewsModel

interface NewsRepository {
    suspend fun getNews():Result<List<NewsModel>>
}