package com.example.testrussia.data

import android.util.Log
import com.example.testrussia.data.source.NewsDataSource
import com.example.testrussia.data.source.local.NewsDataFromDataBase
import com.example.testrussia.news.model.NewsModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class DefaultNewsRepository(
        val remoteDataSource: NewsDataSource,
        val localDataSource: NewsDataFromDataBase,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : NewsRepository {

    private var cashedNews: ConcurrentMap<Int, NewsModel>? = null

    override suspend fun getNews(forceUpdate: Boolean): Result<List<NewsModel>> {
        return withContext(ioDispatcher) {
            if (!forceUpdate) {
                cashedNews?.let { cachedTasks ->
                    return@withContext Result.Success(cachedTasks.values.sortedBy { it.id })
                }
            }

            val news = fetchNewsFromRemoteOrLocal()
            // Refresh the cache with the new news items
            (news as? Result.Success)?.let { refreshCache(it.data) }

            cashedNews?.values?.let { tasks ->
                return@withContext Result.Success(tasks.sortedBy { it.id })
            }

            (news as? Result.Success)?.let {
                if (it.data.isEmpty()) {
                    return@withContext Result.Success(it.data)
                }
            }

            return@withContext Result.Error(Exception("Illegal state"))
        }
    }

    private fun refreshCache(news: List<NewsModel>) {
        cashedNews?.clear()
        news.sortedBy { it.id }.forEach {
            if(cashedNews==null){
                cashedNews = ConcurrentHashMap()
            }
            cashedNews?.put(it.id,it)
        }
    }


    private suspend fun fetchNewsFromRemoteOrLocal(): Result<List<NewsModel>> {
        val remoteNews = remoteDataSource.getNews()
        when (remoteNews) {
            is Result.Error -> Log.d("Nurs","Remote data source fetch failed")
            is Result.Success -> {
                refreshLocalDataSource(remoteNews.data)
                return remoteNews
            }
            else -> throw IllegalStateException()
        }

        // Local if remote fails
        val newsFromLocalSource = localDataSource.getNews()
        if (newsFromLocalSource is Result.Success) return newsFromLocalSource
        return Result.Error(Exception("Error fetching from remote and local"))
    }

    private suspend fun refreshLocalDataSource(news: List<NewsModel>) {
        localDataSource.deleteNews()
        for (newsItem in news) {
            localDataSource.saveNewsItem(newsItem)
        }
    }
}