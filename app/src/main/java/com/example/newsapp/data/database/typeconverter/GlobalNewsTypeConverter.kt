package com.example.newsapp.data.database.typeconverter

import androidx.room.TypeConverter
import com.example.newsapp.models.globalnews.GlobalNews
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GlobalNewsTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun globalNewsToString(globalNews : GlobalNews) : String {
        return gson.toJson(globalNews)
    }

    @TypeConverter
    fun stringToGlobalNews(data : String) : GlobalNews {
        val listType =object : TypeToken<GlobalNews>() {}.type
        return gson.fromJson(data,listType)
    }
}