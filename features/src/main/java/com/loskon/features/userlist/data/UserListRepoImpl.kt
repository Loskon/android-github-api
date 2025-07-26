package com.loskon.features.userlist.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.loskon.database.db.UserDatabase
import com.loskon.database.entity.RemoteKeyEntity
import com.loskon.database.mediator.LocalRemoteMediator
import com.loskon.database.mediator.LocalRemoteMediator2
import com.loskon.database.source.LocalDataSource
import com.loskon.features.model.UserModel
import com.loskon.features.model.toUserEntity
import com.loskon.features.model.toUserModel
import com.loskon.features.userlist.domain.UserListRepository
import com.loskon.network.api.GithubApi
import com.loskon.network.source.NetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

class UserListRepoImpl(
    private val service: GithubApi,
    private val db: UserDatabase,
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource
) : UserListRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getUsers(): Flow<PagingData<UserModel>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = LocalRemoteMediator2(service, db),
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
        return LocalRemoteMediator(db).apply {
            setOnRefreshListener { since, perPage, refresh ->
                val users = networkDataSource.getSearchRepos(since, perPage).map { it.toUserEntity() }
                val endPagination = users.isEmpty()

                Timber.d("apiusers: %s", users)
                Timber.d("endPagination: %s", endPagination)

                val prevKey = if (since == STARTING_PAGE_INDEX) null else since.minus(1)
                val nextKey = if (endPagination) null else since.plus(1)
                val keys = users.map { RemoteKeyEntity(it.id, prevKey, nextKey) }

                if (refresh) localDataSource.clearAll()
                localDataSource.insertUsersAndKeys(keys, users)

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

        private const val STARTING_PAGE_INDEX = 1
        private const val PAGE_SIZE = 20
    }
}