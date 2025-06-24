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

    //override suspend fun initialize() = InitializeAction.LAUNCH_INITIAL_REFRESH

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, UserEntity>
    ): MediatorResult {

        val since = when (loadType) {
            LoadType.REFRESH -> {
                Timber.d("RemoteKey REFRESH: " + 0)
                0
                /*                val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                                Timber.d("RemoteKey REFRESH: " + remoteKey)
                                Timber.d("RemoteKey REFRESH nextKey: " + remoteKey?.nextKey)
                                remoteKey?.nextKey?.minus(SINCE_PAGE_SIZE) ?: STARTING_PAGE_INDEX*/
            }

            LoadType.PREPEND -> {
/*                val remoteKey = getRemoteKeyByFirst(state)
                Timber.d("RemoteKey PREPEND: " + remoteKey?.prevKey)
                remoteKey?.prevKey ?: return MediatorResult.Success(remoteKey != null)*/
                val firstItem = state.firstItemOrNull()
                Timber.d("RemoteKey PREPEND: %s", firstItem?.id)
                firstItem?.id ?: return MediatorResult.Success(true)
                //return MediatorResult.Success(true)
            }

            LoadType.APPEND -> {
                /*                val remoteKey = getRemoteKeyByLast(state)
                                Timber.d("RemoteKey APPEND: " + remoteKey?.nextKey)
                                remoteKey?.nextKey ?: return MediatorResult.Success(remoteKey != null)*/
                val lastItem = state.lastItemOrNull()
                Timber.d("RemoteKey APPEND: %s", lastItem?.id)
                lastItem?.id ?: return MediatorResult.Success(false)
            }
        }

        Timber.d("RemoteKey page: " + since)

        return try {
            val refresh = (loadType == LoadType.REFRESH)
            onRefreshListener?.invoke(since.toInt(), state.config.pageSize, refresh)

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

        private const val STARTING_PAGE_INDEX = 0
        private const val SINCE_PAGE_SIZE = 20
    }
}

/*
@OptIn(ExperimentalPagingApi::class)
class LocalRemoteMediator2(
    private val service: GithubApi,
    private val repoDatabase: UserDatabase
) : RemoteMediator<Int, UserEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, UserEntity>): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                Log.d("Tag22", "RemoteKey REFRESH: $remoteKeys")
                remoteKeys?.nextKey?.minus(20) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for prepend.
                val prevKey = remoteKeys?.prevKey
                Log.d("Tag22", "RemoteKey PREPEND: $prevKey")
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                Log.d("Tag22", "RemoteKey APPEND: $nextKey")
                if (nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                nextKey
            }
        }

        try {
            Log.d("Tag22", "RemoteKey page: $page")
            //val apiResponse = service.getUserSearch(page = page, perPage = state.config.pageSize)
            //val repos = apiResponse.items

            val apiResponse = service.getUsers(since = page, pageSize = state.config.pageSize)
            val repos = apiResponse.body()?.sortedBy { it.id } ?: emptyList()
            val endOfPaginationReached = repos.isEmpty()

            repoDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    repoDatabase.remoteKeyDao().clearRemoteKey()
                    repoDatabase.userDao().clearUsers()
                }

                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - SINCE_PAGE_SIZE
                val nextKey = if (endOfPaginationReached) null else page + SINCE_PAGE_SIZE
                val keys = repos.map { RemoteKeyEntity(userId = it.id ?: 0L, prevKey = prevKey, nextKey = nextKey) }

                repoDatabase.remoteKeyDao().insertKey(keys)
                repoDatabase.userDao().insertUsers(repos.map { it.toUserEntity2() })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, UserEntity>): RemoteKeyEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                repoDatabase.remoteKeyDao().remoteKeyUserId(repo.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, UserEntity>): RemoteKeyEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                repoDatabase.remoteKeyDao().remoteKeyUserId(repo.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, UserEntity>
    ): RemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                repoDatabase.remoteKeyDao().remoteKeyUserId(repoId)
            }
        }
    }

    companion object {

        private const val STARTING_PAGE_INDEX = 1
        private const val SINCE_PAGE_SIZE = 20
    }
}

fun UserDto.toUserEntity2(): UserEntity {
    return UserEntity(
        login = login ?: "",
        id = id ?: 0,
        avatarUrl = avatarUrl ?: "",
        htmlUrl = htmlUrl ?: "",
        type = type ?: "",
        createdAt = createdAt ?: LocalDateTime.now()
    )
}*/
