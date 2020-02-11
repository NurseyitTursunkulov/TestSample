package com.example.testrussia.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testrussia.news.model.NewsModel

@Dao
interface NewsDao {
    @Query("SELECT * FROM NewsModel")
    suspend fun getNews(): List<NewsModel>

    @Query("DELETE  FROM NewsModel")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNewsItem(newsModel: NewsModel)
}