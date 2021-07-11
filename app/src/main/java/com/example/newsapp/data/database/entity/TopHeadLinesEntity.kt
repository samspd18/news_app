package com.example.newsapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.models.topheadlines.TopHeadLines
import com.example.newsapp.util.Constants.Companion.DATABASE_NAME
import com.example.newsapp.util.Constants.Companion.TOP_HEADLINES_TABLE

@Entity(tableName = TOP_HEADLINES_TABLE)
class TopHeadLinesEntity(
    var topHeadLines : TopHeadLines
){
    @PrimaryKey(autoGenerate = false)
    var id : Int = 0

}