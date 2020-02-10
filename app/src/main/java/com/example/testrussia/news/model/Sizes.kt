package com.example.testrussia.news.model

import com.google.gson.annotations.SerializedName

data class Sizes(
    val highres: Any,
    val large: Any,
    val mobile: Any,
    @SerializedName(" post-thumbnail")
    val postthumbnail: Any
)