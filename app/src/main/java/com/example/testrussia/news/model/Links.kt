package com.example.testrussia.news.model

import com.google.gson.annotations.SerializedName

data class Links(
    val about: List<About>,
    val author: List<Author>,
    val collection: List<Collection>,
    val curies: List<Cury>,
    val predecessor_version: List<PredecessorVersion>,
    val replies: List<Reply>,
    val self: List<Self>,
    val version_history: List<VersionHistory>,
    @SerializedName("wp:attachment")
    val wp_attachment: List<WpAttachment>,
    @SerializedName("wp:featuredmedia")
    val wp_featuredmedia: List<WpFeaturedmedia>,
    @SerializedName("wp:term")
    val wp_term: List<WpTerm>
)