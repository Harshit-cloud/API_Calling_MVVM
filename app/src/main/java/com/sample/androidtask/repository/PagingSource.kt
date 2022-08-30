package com.sample.androidtask.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sample.androidtask.api.ApiInterface
import com.sample.androidtask.models.Data
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

const val STARTING_PAGE_INDEX = 1
const val NETWORK_PAGE_SIZE = 2

class PagingSource @Inject constructor(
    private val apiService: ApiInterface,
) : PagingSource<Int, Data>() {
    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params:  LoadParams<Int>):  LoadResult<Int, Data> {
        val position = params.key ?: STARTING_PAGE_INDEX
        try{
            val response =
                apiService.getUsers(ApiInterface.page)
            val repos = response.data
            val nextKey = position + 1
            ApiInterface.page++
            return  LoadResult.Page(
                data = repos!!,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        }
        catch(e:IOException){
            return  LoadResult.Error(e)
        }
        catch(e:HttpException){
            return  LoadResult.Error(e)
        }
        catch(e:Exception){
            return  LoadResult.Error(e)
        }
    }

}
