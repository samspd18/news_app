package com.example.newsapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.data.database.entity.GlobalNewsEntity
import com.example.newsapp.data.database.entity.TopHeadLinesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopHeadLines(topHeadLinesEntity: TopHeadLinesEntity)

    @Query("SELECT * FROM top_head_lines_table ORDER BY id ASC")
    fun readTopHeadLines() : Flow<List<TopHeadLinesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGlobalNews(globalNewsEntity: GlobalNewsEntity)

    @Query("SELECT * FROM global_news_table ORDER BY id ASC")
    fun readGlobalNews() : Flow<List<GlobalNewsEntity>>

}