package com.example.newsapp.models.topheadlines


import com.example.newsapp.models.topheadlines.Article
import com.google.gson.annotations.SerializedName

data class TopHeadLines(
    @SerializedName("articles")
    val articles: List<Article>,
)