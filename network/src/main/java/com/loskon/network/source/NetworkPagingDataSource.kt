package com.loskon.network.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.loskon.network.api.GithubApi
import com.loskon.network.dto.UserDto
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

private const val GITHUB_STARTING_SINCE = 0
//private const val PAGE_SIZE = 30

class NetworkPagingDataSource(
    private val githubApi: GithubApi
) : PagingSource<Int, UserDto>() {

    fun getUsers(): Flow<PagingData<UserDto>> {
        return Pager(
            config = PagingConfig(pageSize = 30, enablePlaceholders = true),
            pagingSourceFactory = { NetworkPagingDataSource(githubApi) }
        ).flow
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserDto> {
        return try {
            val since = params.key ?: GITHUB_STARTING_SINCE
            val response = githubApi.getPagingUsers(since = since, 30)

            LoadResult.Page(
                data = response.body() ?: emptyList(),
                prevKey = null,
                nextKey = since + 1
            )
        } catch (exception: Exception) {
            Timber.d(exception)
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}