package com.example.newsapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.models.globalnews.GlobalNews
import com.example.newsapp.util.Constants.Companion.GLOBAL_NEWS_TABLE

@Entity(tableName = GLOBAL_NEWS_TABLE)
class GlobalNewsEntity(
    var globalNews : GlobalNews
) {
    @PrimaryKey(autoGenerate = false)
    var id : Int = 0
}
