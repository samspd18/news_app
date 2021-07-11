package com.example.newsapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.newsapp.util.Constants.Companion.DEFAULT_CATEGORY
import com.example.newsapp.util.Constants.Companion.PREFERENCES_BACK_ONLINE
import com.example.newsapp.util.Constants.Companion.PREFERENCES_MEAL_TYPE
import com.example.newsapp.util.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import com.example.newsapp.util.Constants.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context : Context) {


    private object PreferenceKeys {
        val selectedCategory = stringPreferencesKey(PREFERENCES_MEAL_TYPE)
        val selectedCategoryId = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)
        val backOnline = booleanPreferencesKey(PREFERENCES_BACK_ONLINE)
    }

    private val dataStore : DataStore<Preferences> = context.dataStore

    suspend fun saveCategoryType(
        categoryType : String,
        categoryTypeId : Int
    ){
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedCategory] = categoryType
            preferences[PreferenceKeys.selectedCategoryId] = categoryTypeId
        }
    }

    suspend fun saveBackOnline(backOnline : Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.backOnline] = backOnline
        }
    }

    val readCategoryType : Flow<CategoryType> = dataStore.data
        .catch { exception ->
            if(exception is IOException) {
                emit(emptyPreferences())
            }else{
                throw exception
            }
        }
        .map { preferences ->
            val selectedCategoryType = preferences[PreferenceKeys.selectedCategory] ?: DEFAULT_CATEGORY
            val selectedCategoryTypeId = preferences[PreferenceKeys.selectedCategoryId] ?: 0
            CategoryType(
                selectedCategoryType,
                selectedCategoryTypeId
            )
        }

    val readBackOnline : Flow<Boolean> = dataStore.data
        .catch { exception ->
            if(exception is IOException) {
                emit(emptyPreferences())
            }else {
                throw exception
            }
        }
        .map { preferences ->
            val backOnline = preferences[PreferenceKeys.backOnline] ?: false
            backOnline
        }
}

data class CategoryType(
    val selectedCategoryType : String,
    val selectedCategoryTypeId : Int
)