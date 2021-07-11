package com.example.newsapp.models.globalnews


import com.google.gson.annotations.SerializedName

data class GlobalNews(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)