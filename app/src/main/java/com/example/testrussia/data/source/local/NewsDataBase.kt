package com.example.testrussia.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testrussia.news.model.NewsModel

@Database(entities = [NewsModel::class], version = 1, exportSchema = false)
abstract class NewsDataBase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {
        private var INSTANCE: NewsDataBase? = null

        private val lock = Any()

        fun getInstance(context: Context): NewsDataBase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        NewsDataBase::class.java, "NewsDataBase.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}
