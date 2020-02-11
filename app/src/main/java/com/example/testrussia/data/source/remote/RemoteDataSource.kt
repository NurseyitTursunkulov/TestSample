package com.example.testrussia.data.source.remote

import com.example.testrussia.data.Result
import com.example.testrussia.data.source.NewsDataSource
import com.example.testrussia.news.model.NewsModel

class RemoteDataSource(val newsServiceApi: NewsServiceApi) : NewsDataSource {
    override suspend fun getNews(): Result<List<NewsModel>> {
        try {
            val call = newsServiceApi.getNews().await()
            if (call.isSuccessful) {
                val list =  ArrayList<com.example.testrussia.news.model.NewsModel>()

                call.body()?.forEach {
                    list.add(it)
                }
                return Result.Success(list)
            } else {
                return Result.Error(Exception("no connection"))
            }
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }
}