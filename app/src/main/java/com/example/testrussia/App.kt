package com.example.testrussia

import android.app.Application
import android.util.Log
import com.example.testrussia.data.DefaultNewsRepository
import com.example.testrussia.data.NewsRepository
import com.example.testrussia.data.NewsServiceApi
import com.example.testrussia.news.NewsViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class App  : Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin
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
//            .baseUrl("http://192.168.43.9:5000")//redme
//            .baseUrl("http://192.168.0.109:5000/")//baza
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
        }

        factory { get<Retrofit>().create(NewsServiceApi::class.java) }
        factory<NewsRepository> { DefaultNewsRepository(newsServiceApi = get()) }

        viewModel { NewsViewModel(newsRepository = get()) }
    }
}