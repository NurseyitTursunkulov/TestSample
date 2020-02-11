package com.example.testrussia.news.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class NewsModel(
    @Embedded
    var cat_cover: CatCover? = null,
    @Embedded
    var title: Title? = null,
    @Embedded(prefix = "foo_")
    var content: Content? = null,
    @PrimaryKey
    var id: Int = 0
)