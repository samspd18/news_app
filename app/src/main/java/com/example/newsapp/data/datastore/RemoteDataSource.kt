package com.example.newsapp.data.datastore

import com.example.newsapp.data.network.NewsApi
import com.example.newsapp.models.globalnews.GlobalNews
import com.example.newsapp.models.topheadlines.TopHeadLines
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val newsApi : NewsApi
) {

    suspend fun getTopHeadLines(queries : Map<String,String>) : Response<TopHeadLines> {
        return newsApi.getTopHeadLines(queries)
    }

    suspend fun getGlobalNews(queries: Map<String, String>) : Response<GlobalNews>{
        return newsApi.getGlobalNews(queries)
    }

    suspend fun getSearchNews(searchQuery : Map<String,String>) : Response<GlobalNews> {
        return newsApi.getSearchNews(searchQuery)
    }

}