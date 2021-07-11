package com.example.newsapp.data.datastore

import com.example.newsapp.data.database.NewsDao
import com.example.newsapp.data.database.entity.GlobalNewsEntity
import com.example.newsapp.data.database.entity.TopHeadLinesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val newsDao: NewsDao
){
    fun readTopHeadLineDataBase() : Flow<List<TopHeadLinesEntity>> {
        return newsDao.readTopHeadLines()
    }

    suspend fun insertTopHeadLines(topHeadLinesEntity: TopHeadLinesEntity) {
        newsDao.insertTopHeadLines(topHeadLinesEntity)
    }

    fun readGlobalNewsDataBase() : Flow<List<GlobalNewsEntity>> {
        return newsDao.readGlobalNews()
    }

    suspend fun insertGlobalNews(globalNewsEntity: GlobalNewsEntity) {
        newsDao.insertGlobalNews(globalNewsEntity)
    }
}