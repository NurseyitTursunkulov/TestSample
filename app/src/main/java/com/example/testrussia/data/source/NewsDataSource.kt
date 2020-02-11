package com.example.testrussia.data.source

import com.example.testrussia.data.Result
import com.example.testrussia.news.model.NewsModel

interface NewsDataSource {
    suspend fun getNews(): Result<List<NewsModel>>
}