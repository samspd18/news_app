package com.example.newsapp.viewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.newsapp.data.Repository
import com.example.newsapp.data.database.entity.GlobalNewsEntity
import com.example.newsapp.data.database.entity.TopHeadLinesEntity
import com.example.newsapp.models.globalnews.GlobalNews
import com.example.newsapp.models.topheadlines.TopHeadLines
import com.example.newsapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    /**ROOM DATABASE*/

    val readTopHeadLines : LiveData<List<TopHeadLinesEntity>> = repository.local.readTopHeadLineDataBase().asLiveData()
    val readGlobalNews : LiveData<List<GlobalNewsEntity>> = repository.local.readGlobalNewsDataBase().asLiveData()

    private fun insertTopHeadLines(topHeadLinesEntity: TopHeadLinesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertTopHeadLines(topHeadLinesEntity)

        }

    private fun insertGlobalNews(globalNewsEntity: GlobalNewsEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertGlobalNews(globalNewsEntity)
        }

    /** RETROFIT */

    var topHeadLinesResponse : MutableLiveData<NetworkResult<TopHeadLines>> = MutableLiveData()
    var globalNewsResponse : MutableLiveData<NetworkResult<GlobalNews>> = MutableLiveData()
    var searchNewsResponse : MutableLiveData<NetworkResult<GlobalNews>> = MutableLiveData()

    /**
     * For top headlines news
     * starts here
     */
    fun getTopHeadlines(queries : Map<String,String>) = viewModelScope.launch {
        getTopHeadLinesSafeCall(queries)
    }

    fun getSearchNews(searchQuery : Map<String,String>) =
        viewModelScope.launch {
            searchNewsSafeCall(searchQuery)
        }

    private suspend fun getTopHeadLinesSafeCall(queries: Map<String, String>) {

        topHeadLinesResponse.value = NetworkResult.Loading()

        if(hasInternetConnection()){
            try {
                val response = repository.remote.getTopHeadLines(queries)
                topHeadLinesResponse.value = handleTopHeadLinesResponse(response)

                val topHeadLines = topHeadLinesResponse.value!!.data
                if(topHeadLines!= null){
                    offLineCacheTopHeadLines(topHeadLines)
                }
            }catch (e : Exception){
                topHeadLinesResponse.value = NetworkResult.Error("News not found")
            }
        }else{
            topHeadLinesResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }


    private suspend fun searchNewsSafeCall(searchQuery: Map<String, String>) {
        searchNewsResponse.value = NetworkResult.Loading()
        if(hasInternetConnection()){
            try {
                val response = repository.remote.getSearchNews(searchQuery)
                searchNewsResponse.value = handleGlobalNewsResponse(response)
            }catch (e : Exception){
                searchNewsResponse.value = NetworkResult.Error("Not Found Any News Regarding this")
            }
        }else{
            searchNewsResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }


    private fun offLineCacheTopHeadLines(topHeadLines: TopHeadLines) {
        val topHeadLinesEntity = TopHeadLinesEntity(topHeadLines)
        insertTopHeadLines(topHeadLinesEntity)
    }

    private fun handleTopHeadLinesResponse(response: Response<TopHeadLines>): NetworkResult<TopHeadLines> {
        when{
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("TimeOut")
            }
            response.code() == 400 -> {
                return NetworkResult.Error("The request was unacceptable, often due to a missing or misconfigured parameter.")
            }
            response.code() == 401 -> {
                return NetworkResult.Error("Your API key was missing from the request, or wasn't correct.")
            }
            response.code() == 429 -> {
                return NetworkResult.Error("You made too many requests within a window of time and have been rate limited. Back off for a while.")
            }
            response.code() == 500 -> {
                return NetworkResult.Error("Something went wrong on our side.")
            }
            response.body()!!.articles.isNullOrEmpty() -> {
                return NetworkResult.Error("News not found")
            }
            response.code() == 200 -> {
                val newsTopLine = response.body()
                return NetworkResult.Success(newsTopLine!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    /**
     * Top headline function ended here
     */

    /**
     * For Global news
     * Starts here
     */

    fun getGlobalNews(queries : Map<String,String>) = viewModelScope.launch {
        getGlobalNewsSafeCall(queries)
    }

    private suspend fun getGlobalNewsSafeCall(queries: Map<String, String>) {

        globalNewsResponse.value = NetworkResult.Loading()

        if(hasInternetConnection()){
            try {
                val response = repository.remote.getGlobalNews(queries)
                globalNewsResponse.value = handleGlobalNewsResponse(response)

                val globalNews = globalNewsResponse.value!!.data
                if(globalNews!= null){
                    offLineCacheGlobalNews(globalNews)
                }
            }catch (e : Exception){
                globalNewsResponse.value = NetworkResult.Error("News not found")
            }
        }else{
            globalNewsResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }

    private fun offLineCacheGlobalNews(globalNews: GlobalNews) {
        val globalNewsEntity = GlobalNewsEntity(globalNews)
        insertGlobalNews(globalNewsEntity)
    }

    private fun handleGlobalNewsResponse(response: Response<GlobalNews>): NetworkResult<GlobalNews> {
        when{
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("TimeOut")
            }
            response.code() == 400 -> {
                return NetworkResult.Error("The request was unacceptable, often due to a missing or misconfigured parameter.")
            }
            response.code() == 401 -> {
                return NetworkResult.Error("Your API key was missing from the request, or wasn't correct.")
            }
            response.code() == 429 -> {
                return NetworkResult.Error("You made too many requests within a window of time and have been rate limited. Back off for a while.")
            }
            response.code() == 500 -> {
                return NetworkResult.Error("Something went wrong on our side.")
            }
            response.body()!!.articles.isNullOrEmpty() -> {
                return NetworkResult.Error("News not found")
            }
            response.code() == 200 -> {
                val globalNews = response.body()
                return NetworkResult.Success(globalNews!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    /**
     * End of global news function
     */

    /**
     * Checking the internet Connection
     */
    private fun hasInternetConnection() : Boolean{
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}