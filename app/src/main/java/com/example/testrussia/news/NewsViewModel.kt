package com.example.testrussia.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testrussia.Event
import com.example.testrussia.data.NewsRepository
import com.example.testrussia.data.Result
import com.example.testrussia.news.model.NewsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel(val newsRepository: NewsRepository) : ViewModel() {

    private val _newsLiveList = MutableLiveData<List<NewsModel>>().apply { value = emptyList() }
    val newsLiveList: LiveData<List<NewsModel>> = _newsLiveList

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    init {
        loadNews(true)
    }

    private fun loadNews(forceUpdate: Boolean = false) {
        viewModelScope.launch {
            _dataLoading.postValue(true)
            withContext(Dispatchers.IO) {
                val news = newsRepository.getNews(forceUpdate)
                when (news) {
                    is Result.Success -> {
                        val newsList = arrayListOf<NewsModel>()
                        news.data.forEach {
                            newsList.add(it)
                        }
                        _newsLiveList.postValue(newsList)
                    }

                    is Result.Error -> {
                        _snackbarText.postValue(Event(news.exception.toString()))
                    }

                    is Result.Loading -> {
                    }
                }

                _dataLoading.postValue(false)
            }

        }
    }

    fun refresh() {
        loadNews(forceUpdate = true)
    }
}