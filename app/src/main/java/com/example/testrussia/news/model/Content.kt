package com.example.testrussia.news.model

import androidx.room.Entity

@Entity
data class Content(
    var `protected`: Boolean = false,
    var rendered: String = ""
)