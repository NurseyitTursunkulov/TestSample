package com.example.testrussia.news.model

import androidx.room.Embedded
import androidx.room.Entity

@Entity
data class CatCover(
    @Embedded
    val sizes: Sizes
)