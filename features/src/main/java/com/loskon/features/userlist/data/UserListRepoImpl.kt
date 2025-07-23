package com.loskon.features.userlist.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.loskon.database.entity.RemoteKeyEntity
import com.loskon.database.mediator.LocalRemoteMediator
import com.loskon.database.source.LocalDataSource
import com.loskon.features.model.UserModel
import com.loskon.features.model.toUserEntity
import com.loskon.features.model.toUserModel
import com.loskon.features.userlist.domain.UserListRepository
import com.loskon.network.source.NetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

class UserListRepoImpl(
    private val localRemoteMediator: LocalRemoteMediator,
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource
) : UserListRepository {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getUsers(): Flow<PagingData<UserModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = SINCE_PAGE_SIZE,
                enablePlaceholders = false,
                maxSize = 3 * SINCE_PAGE_SIZE,
                initialLoadSize = SINCE_PAGE_SIZE
            ),
            remoteMediator = getRemoteMediator(),
            pagingSourceFactory = { localDataSource.getUsers() }
        ).flow.map { pagingData ->
            pagingData.map {
                val ss = it.toUserModel()
                Timber.d("USER DATA: %s", ss)
                ss
            }
        }
    }

    private fun getRemoteMediator(): LocalRemoteMediator {
        return localRemoteMediator.apply {
            setOnRefreshListener { since, pageSize, refresh ->
                val users = networkDataSource.getUsers(since, pageSize).map { it.toUserEntity() }
                val endPagination = users.isEmpty()

                Timber.d("apiusers: %s", users)
                Timber.d("endPagination: %s", endPagination)

                val prevKey = if (since == STARTING_PAGE_INDEX) null else since
                val nextKey = if (endPagination) null else users.lastOrNull()?.id
                val key = users.map { RemoteKeyEntity(it.id, prevKey, nextKey) }.last()

                if (refresh) localDataSource.clearAll()
                localDataSource.insertUsersAndKey(key, users)

                setEndPagination(endPagination)
            }
        }
    }

    override suspend fun getCachedUsers(): List<UserModel>? {
        return localDataSource.getCachedUsers()?.map { it.toUserModel() }
    }

    override suspend fun setUsersInCache(users: List<UserModel>) {
        localDataSource.setUsersInCache(users.map { it.toUserEntity() })
    }

    companion object {

        private const val STARTING_PAGE_INDEX = 0
        private const val SINCE_PAGE_SIZE = 20
    }
}