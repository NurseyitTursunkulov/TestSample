package com.example.testrussia.data

import android.util.Log
import com.example.testrussia.news.model.NewsModel

class DefaultNewsRepository(val newsServiceApi: NewsServiceApi) : NewsRepository {
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