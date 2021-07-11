package com.example.newsapp.data

import com.example.newsapp.data.datastore.LocalDataSource
import com.example.newsapp.data.datastore.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource : LocalDataSource
) {
    val remote = remoteDataSource
    val local = localDataSource
}