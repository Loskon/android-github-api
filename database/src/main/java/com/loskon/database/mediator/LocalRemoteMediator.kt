package com.loskon.database.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.loskon.database.db.UserDatabase
import com.loskon.database.entity.RemoteKeyEntity
import com.loskon.database.entity.UserEntity
import com.loskon.network.api.GithubApi
import com.loskon.network.dto.RepoDto
import timber.log.Timber
import java.time.LocalDateTime

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

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                Timber.d("RemoteKey REFRESH: %s", remoteKeys?.nextKey)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKey = getRemoteKeyByFirst(state)
                Timber.d("RemoteKey PREPEND: %s", remoteKey?.prevKey)
                remoteKey?.prevKey ?: return MediatorResult.Success(remoteKey != null)
            }

            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyByLast(state)
                Timber.d("RemoteKey APPEND: %s", remoteKey?.nextKey)
                remoteKey?.nextKey ?: return MediatorResult.Success(remoteKey != null)
            }
        }

        Timber.d("RemoteKey page: " + page)

        return try {
            val refresh = (loadType == LoadType.REFRESH)
            onRefreshListener?.invoke(page, state.config.pageSize, refresh)

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
    }
}

@OptIn(ExperimentalPagingApi::class)
class LocalRemoteMediator2(
    private val service: GithubApi,
    private val database: UserDatabase
) : RemoteMediator<Int, UserEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, UserEntity>): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                Timber.d("RemoteKey REFRESH: %s", remoteKeys?.nextKey)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                Timber.d("RemoteKey PREPEND: %s", prevKey)
                if (prevKey == null) return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                Timber.d("RemoteKey APPEND: %s", nextKey)
                if (nextKey == null) return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            Timber.d("RemoteKey page: " + page)
            val apiResponse = service.getSearchRepos(query = "Android", page = page, perPage = state.config.pageSize)
            val repos = apiResponse.body()?.items ?: throw Exception("Empty body")
            val endOfPaginationReached = repos.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeyDao().clearRemoteKey()
                    database.userDao().clearUsers()
                }

                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = repos.map { RemoteKeyEntity(userId = it.id?.toInt() ?: 0, prevKey = prevKey, nextKey = nextKey) }

                database.remoteKeyDao().insertKeys(keys)
                database.userDao().setUsersInCache(repos.map { it.toUserEntity2() })
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            Timber.d(exception)
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, UserEntity>): RemoteKeyEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                database.remoteKeyDao().remoteKeyUserId(repo.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, UserEntity>): RemoteKeyEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                database.remoteKeyDao().remoteKeyUserId(repo.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, UserEntity>
    ): RemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                database.remoteKeyDao().remoteKeyUserId(repoId)
            }
        }
    }

    companion object {

        private const val STARTING_PAGE_INDEX = 1
        private const val SINCE_PAGE_SIZE = 20
    }
}

fun RepoDto.toUserEntity2(): UserEntity {
    return UserEntity(
        id = id?.toInt() ?: 0,
        login = fullName,
        avatarUrl = "",
        htmlUrl = "",
        type = "",
        createdAt = createdAt ?: LocalDateTime.now(),
        stars = stars
    )
}
