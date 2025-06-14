package com.loskon.network.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.loskon.network.api.GithubApi
import com.loskon.network.dto.UserDto
import timber.log.Timber

class NetworkPagingDataSource(
    private val githubApi: GithubApi
) : PagingSource<Int, UserDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserDto> {
        return try {
            val since = params.key ?: STARTING_PAGE_INDEX
            val response = githubApi.getUsers(since, params.loadSize).body() ?: emptyList()
            val prevKey = if (since == STARTING_PAGE_INDEX) null else since.minus(PAGE_SIZE)
            val nextKey = if (response.isEmpty()) null else since.plus(PAGE_SIZE)

            LoadResult.Page(
                data = response,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            Timber.e(e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(PAGE_SIZE)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(PAGE_SIZE)
        }
    }

    companion object {

        private const val STARTING_PAGE_INDEX = 1
        private const val PAGE_SIZE = 20
    }
}