package com.sample.androidtask.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.sample.androidtask.api.ApiInterface
import com.sample.androidtask.models.Data
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor(
    private val service: ApiInterface
) {

    fun getUsers(): LiveData<PagingData<Data>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
              PagingSource(service)
            }
        ).liveData

    }

}