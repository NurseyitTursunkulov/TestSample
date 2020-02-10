package com.example.testrussia.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testrussia.data.NewsRepository
import com.example.testrussia.data.Result
import kotlinx.coroutines.launch

class NewsViewModel(val newsRepository: NewsRepository) : ViewModel() {
    init {
        Log.d("Nurs", "init")
        viewModelScope.launch {
            val news = newsRepository.getNews()
            when (news) {
                is Result.Success -> {
                    news.data.forEach {
                        Log.d("Nurs2", "${it}")
                    }

                }

                is Result.Error -> {
                    Log.d("Nurs2", "${news.exception}")
                }

                is Result.Loading -> {
                    Log.d("Nurs2", "loading")
                }

            }

        }
    }
}