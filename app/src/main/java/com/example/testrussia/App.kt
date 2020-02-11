package com.example.testrussia

import android.app.Application
import com.example.testrussia.data.DefaultNewsRepository
import com.example.testrussia.data.NewsRepository
import com.example.testrussia.data.source.local.LocalDataSource
import com.example.testrussia.data.source.local.NewsDao
import com.example.testrussia.data.source.local.NewsDataBase
import com.example.testrussia.data.source.remote.NewsServiceApi
import com.example.testrussia.data.source.remote.RemoteDataSource
import com.example.testrussia.news.NewsViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }
    }


    val appModule = module {

        factory {
            OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build()
        }

        single {
            Retrofit.Builder()
                .client(get())
                .baseUrl("https://lifehacker.ru/api/wp/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
        }

        factory { get<Retrofit>().create(NewsServiceApi::class.java) }

        factory<NewsRepository> {
            DefaultNewsRepository(
                remoteDataSource = RemoteDataSource(newsServiceApi = get()),
                localDataSource = LocalDataSource(newsDao = get())
            )
        }

        viewModel { NewsViewModel(newsRepository = get()) }

        single<NewsDao> { NewsDataBase.getInstance(androidApplication()).newsDao() }
    }
}