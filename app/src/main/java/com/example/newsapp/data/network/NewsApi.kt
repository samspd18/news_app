package com.example.newsapp.data.network
import com.example.newsapp.models.globalnews.GlobalNews
import com.example.newsapp.models.topheadlines.TopHeadLines
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NewsApi {

    //For top headlines
    @GET("top-headlines")
    suspend fun getTopHeadLines(
        @QueryMap queries : Map<String,String>
    ) : Response<TopHeadLines>

    //For global news
    @GET("everything")
    suspend fun getGlobalNews(
        @QueryMap queries: Map<String, String>
    ) : Response<GlobalNews>

    //For search
    @GET("everything")
    suspend fun getSearchNews(
        @QueryMap queries: Map<String, String>
    ) : Response<GlobalNews>
}