package com.example.testrussia

import androidx.annotation.VisibleForTesting
import com.example.testrussia.data.NewsRepository
import com.example.testrussia.data.Result
import com.example.testrussia.news.model.NewsModel

class FakeRepository : NewsRepository {
    val newsServiceData: LinkedHashMap<Int,NewsModel> = LinkedHashMap()
    override suspend fun getNews(forceUpdate: Boolean): Result<List<NewsModel>> {
        return Result.Success(newsServiceData.values.toList())
    }

    @VisibleForTesting
    fun addNews(vararg news: NewsModel) {
        for (newsItem in news) {
            newsServiceData[newsItem.id] = newsItem
        }
    }
}