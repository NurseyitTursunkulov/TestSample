package com.example.testrussia.data.source.local

import com.example.testrussia.data.Result
import com.example.testrussia.news.model.NewsModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDataSource(
    private val newsDao: NewsDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : NewsDataFromDataBase {
    override suspend fun getNews(): Result<List<NewsModel>> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(newsDao.getNews())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun deleteNews() {
        newsDao.deleteAll()
    }

    override suspend fun saveNewsItem(newsModel: NewsModel) {
        newsDao.saveNewsItem(newsModel)
    }

}