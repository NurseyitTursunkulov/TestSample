package com.example.testrussia

import com.example.testrussia.data.source.remote.NewsServiceApi
import com.example.testrussia.news.model.NewsModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import retrofit2.Response

//class FakeServiceApi : NewsServiceApi {
//    override fun getNews(): Deferred<Response<List<NewsModel>>> {
//        return Job(Response(listOf<NewsModel>()))
//    }
//}