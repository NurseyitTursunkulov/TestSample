package com.example.testrussia.data.source.local

import com.example.testrussia.data.source.NewsDataSource
import com.example.testrussia.news.model.NewsModel

interface NewsDataFromDataBase : NewsDataSource {
    suspend fun deleteNews()
    suspend fun saveNewsItem(newsModel: NewsModel)
}