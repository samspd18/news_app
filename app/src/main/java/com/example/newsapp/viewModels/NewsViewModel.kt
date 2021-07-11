package com.example.newsapp.viewModels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.DataStoreRepository
import com.example.newsapp.util.Constants
import com.example.newsapp.util.Constants.Companion.API_KEY
import com.example.newsapp.util.Constants.Companion.DEFAULT_CATEGORY
import com.example.newsapp.util.Constants.Companion.DEFAULT_PAGE_SIZE_NUMBER
import com.example.newsapp.util.Constants.Companion.POPULARITY
import com.example.newsapp.util.Constants.Companion.QUERY_API_KEY
import com.example.newsapp.util.Constants.Companion.QUERY_CATEGORY
import com.example.newsapp.util.Constants.Companion.QUERY_COUNTRY
import com.example.newsapp.util.Constants.Companion.QUERY_PAGE_SIZE
import com.example.newsapp.util.Constants.Companion.QUERY_SEARCH
import com.example.newsapp.util.Constants.Companion.QUERY_SORT_BY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    application : Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application)  {

    private var categoryType = DEFAULT_CATEGORY
    var networkStatus = false
    var backOnline = false

    val readCategoryType = dataStoreRepository.readCategoryType
    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()

    fun saveCategoryType(categoryType : String , categoryTypeId : Int) =
        viewModelScope.launch (Dispatchers.IO) {
            dataStoreRepository.saveCategoryType(categoryType,categoryTypeId)
        }

    fun saveBackOnline(backOnline : Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(backOnline)
        }

    //for the top headlines news
    fun applyTopHeadLinesQueries() : HashMap<String,String> {
        val queries : HashMap<String,String> = HashMap()

        viewModelScope.launch {
            readCategoryType.collect { value ->
                categoryType = value.selectedCategoryType
            }
        }

        queries[QUERY_PAGE_SIZE] = DEFAULT_PAGE_SIZE_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_CATEGORY] = categoryType
        queries[QUERY_COUNTRY] = "in"

        return queries
    }

    //for the global news

    fun applyGlobalNewsQueries() : HashMap<String,String> {
        val queries : HashMap<String,String> = HashMap()

        queries[QUERY_PAGE_SIZE] = "100"
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_SEARCH] = "world news"

        return queries
    }

    fun applySearchQuery(searchQuery : String) : HashMap<String,String> {
        val queries : HashMap<String,String> = HashMap()
        queries[QUERY_SEARCH] = searchQuery
        queries[QUERY_PAGE_SIZE] = "100"
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_SORT_BY] = POPULARITY

        return queries
    }

    fun showNetworkStatus(){
        if(!networkStatus){
            Toast.makeText(getApplication(), "No internet connection", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        }else if(networkStatus){
            if(backOnline){
                Toast.makeText(getApplication(), "We are back online", Toast.LENGTH_SHORT).show()
                saveBackOnline(false)
            }
        }
    }

}