package com.example.newsapp.bindingadapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.example.newsapp.data.database.entity.TopHeadLinesEntity
import com.example.newsapp.models.topheadlines.TopHeadLines
import com.example.newsapp.util.NetworkResult

class TopHeadLInesBinding {

    companion object {

        @BindingAdapter("readApiResponse","readDatabase",requireAll = true)
        @JvmStatic
        fun handleReadDataErrors(
            view : View,
            apiResponse : NetworkResult<TopHeadLines>?,
            database : List<TopHeadLinesEntity>?
        ) {
             when(view){
                 is ImageView -> {
                     view.isVisible = apiResponse is NetworkResult.Error && database.isNullOrEmpty()
                 }
                 is TextView -> {
                     view.isVisible = apiResponse is NetworkResult.Error && database.isNullOrEmpty()
                     view.text = apiResponse?.message.toString()
                 }
             }
        }
    }
}