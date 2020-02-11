package com.example.testrussia.news.model

import com.google.gson.annotations.SerializedName

data class Sizes(
    val highres: String,
    val large: String,
    val mobile: String,
    @SerializedName("post-thumbnail")
    val post_thumbnail: String
)