package com.example.newsapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.data.database.entity.GlobalNewsEntity
import com.example.newsapp.data.database.entity.TopHeadLinesEntity
import com.example.newsapp.data.database.typeconverter.GlobalNewsTypeConverter
import com.example.newsapp.data.database.typeconverter.TopHeadLinesTypeConverter

@Database(
    entities = [TopHeadLinesEntity::class, GlobalNewsEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TopHeadLinesTypeConverter::class,GlobalNewsTypeConverter::class)

abstract class NewsDataBase: RoomDatabase() {

    abstract fun dataBaseLinesDao(): NewsDao

}
