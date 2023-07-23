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
            val position = params.key ?: GITHUB_STARTING_POSITION
            val response = githubApi.getSearchUsers(page = position, perPage = params.loadSize).body()?.items ?: emptyList()
            val prevKey = if (position == GITHUB_STARTING_POSITION) null else position - 1
            val nextKey = if (response.isEmpty()) null else position + 1

            Timber.d("PagingTag: since=" + position + " , key=" + params.key + " , prevKey=" + prevKey+ " ,nextKey=" + nextKey + " data=" + response.map { it.id })

            LoadResult.Page(
                data = response,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            Timber.e(exception)
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(NETWORK_PAGE_SIZE)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(NETWORK_PAGE_SIZE)
        }
    }

    companion object {
        private const val GITHUB_STARTING_POSITION = 0
        const val NETWORK_PAGE_SIZE = 20
    }
}