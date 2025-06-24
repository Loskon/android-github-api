package com.loskon.network.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.loskon.network.api.GithubApi
import com.loskon.network.dto.UserDto
import timber.log.Timber
import java.io.IOException

class NetworkPagingDataSource(
    private val githubApi: GithubApi
) : PagingSource<Int, UserDto>() {

    private var usersLoadListener: (suspend (users: List<UserDto>) -> Unit)? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserDto> {
        return try {
            val since = params.key ?: STARTING_PAGE_INDEX
            val users = getUsers(since, params.loadSize)
            val prevKey = if (since == STARTING_PAGE_INDEX) null else since.minus(PAGE_SIZE)
            val nextKey = if (users.isEmpty()) null else since.plus(PAGE_SIZE)

            LoadResult.Page(
                data = users,
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
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(PAGE_SIZE)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(PAGE_SIZE)
        }
    }

    suspend fun getUsers(since: Int, pageSize: Int): List<UserDto> {
        val response = githubApi.getUsers(since, pageSize)

        return if (response.isSuccessful) {
            response.body() ?: throw IOException("Empty body")
        } else {
            throw IOException("Http status code: " + response.code())
        }
    }

    fun setUsersLoadListener(usersLoadListener: ((suspend (users: List<UserDto>) -> Unit)?)) {
        this.usersLoadListener = usersLoadListener
    }

    companion object {

        private const val STARTING_PAGE_INDEX = 0
        private const val PAGE_SIZE = 20
    }
}