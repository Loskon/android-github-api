package com.loskon.database.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.loskon.database.db.UserDatabase
import com.loskon.database.entity.RemoteKeyEntity
import com.loskon.database.entity.UserEntity
import timber.log.Timber

@OptIn(ExperimentalPagingApi::class)
class LocalRemoteMediator(
    private val userDatabase: UserDatabase
) : RemoteMediator<Int, UserEntity>() {

    private var onRefreshListener: (suspend (since: Int, pageSize: Int, refresh: Boolean) -> Unit)? = null
    private var endPagination: Boolean? = null

    override suspend fun initialize() = InitializeAction.LAUNCH_INITIAL_REFRESH

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, UserEntity>
    ): MediatorResult {

        val since = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                Timber.d("RemoteKey REFRESH: " + remoteKey)
                remoteKey?.nextKey?.minus(SINCE_PAGE_SIZE) ?: STARTING_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKey = getRemoteKeyByFirst(state)
                Timber.d("RemoteKey PREPEND: " + remoteKey)
                remoteKey?.prevKey ?: return MediatorResult.Success(remoteKey != null)
            }

            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyByLast(state)
                Timber.d("RemoteKey APPEND: " + remoteKey)
                remoteKey?.nextKey ?: return MediatorResult.Success(remoteKey != null)
            }
        }

        return try {
            val refresh = (loadType == LoadType.REFRESH)
            onRefreshListener?.invoke(since, state.config.pageSize, refresh)

            MediatorResult.Success(endPagination == true)
        } catch (exception: Exception) {
            Timber.e(exception)
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, UserEntity>
    ): RemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { userId ->
                userDatabase.remoteKeyDao().remoteKeyUserId(userId)
            }
        }
    }

    private suspend fun getRemoteKeyByFirst(
        state: PagingState<Int, UserEntity>
    ): RemoteKeyEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { userDatabase.remoteKeyDao().remoteKeyUserId(it.id) }
    }

    private suspend fun getRemoteKeyByLast(
        state: PagingState<Int, UserEntity>
    ): RemoteKeyEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { userDatabase.remoteKeyDao().remoteKeyUserId(it.id) }
    }

    fun setOnRefreshListener(onRefreshListener: (suspend (since: Int, pageSize: Int, refresh: Boolean) -> Unit)?) {
        this.onRefreshListener = onRefreshListener
    }

    fun setEndPagination(endPagination: Boolean?) {
        this.endPagination = endPagination
    }

    companion object {

        private const val STARTING_PAGE_INDEX = 1
        private const val SINCE_PAGE_SIZE = 20
    }
}

/*            val apiResponse = githubApi.getUsers(since, state.config.pageSize)

            val response = apiResponse.body() ?: emptyList()
            val endOfPaginationReached = response.isEmpty()

            val prevKey = if (since == STARTING_PAGE) null else since.minus(SINCE_SIZE)
            val nextKey = if (endOfPaginationReached) null else since.plus(SINCE_SIZE)
            val keys = response.map { RemoteKeyEntity(it.id ?: 0L, prevKey, nextKey) }

            userDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    userDatabase.remoteKeyDao().clearRemoteKey()
                    userDatabase.userDao().clearUsers()
                }

                userDatabase.remoteKeyDao().insertKey(keys)
                userDatabase.userDao().insertUsers(response.map { it.toUserConvert() })
            }*/