package com.example.testrussia.data

import com.example.testrussia.news.model.NewsModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface NewsServiceApi {
    @GET("posts/")
    fun getNews(): Deferred<Response<List<NewsModel>>>
}