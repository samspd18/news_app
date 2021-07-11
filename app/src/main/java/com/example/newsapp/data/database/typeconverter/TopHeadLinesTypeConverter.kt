package com.example.newsapp.data.database.typeconverter

import androidx.room.TypeConverter
import com.example.newsapp.models.topheadlines.TopHeadLines
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TopHeadLinesTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun topHeadLinesToString(topHeadLines : TopHeadLines) : String {
        return gson.toJson(topHeadLines)
    }

    @TypeConverter
    fun stringToTopHeadLines(data : String) : TopHeadLines {
        val listType =object : TypeToken<TopHeadLines>() {}.type
        return gson.fromJson(data,listType)
    }
}