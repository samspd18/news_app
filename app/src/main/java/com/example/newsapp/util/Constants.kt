package com.example.newsapp.util

class Constants {

    companion object {

        const val BASE_URL = "https://newsapi.org/v2/"
        const val API_KEY = "1a5e9fb04ed64f969b5a88f21e2dfa12"

        //API Query Key
        const val QUERY_PAGE_SIZE = "pageSize"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_CATEGORY = "category"
        const val QUERY_COUNTRY = "country"
        const val QUERY_SEARCH = "q"
        const val QUERY_SORT_BY = "sortBy"

        //Values
        const val POPULARITY = "popularity"

        //For room library data base
        const val DATABASE_NAME = "news_database"
        const val TOP_HEADLINES_TABLE = "top_head_lines_table"
        const val GLOBAL_NEWS_TABLE = "global_news_table"

        //Bottom Sheet Preferences
        const val DEFAULT_PAGE_SIZE_NUMBER = "100"
        const val DEFAULT_CATEGORY = "general"

        const val PREFERENCES_NAME = "news_preferences"
        const val PREFERENCES_MEAL_TYPE = "categoryType"
        const val PREFERENCES_MEAL_TYPE_ID = "categoryTypeId"
        const val PREFERENCES_BACK_ONLINE = "backOnline"

    }
}