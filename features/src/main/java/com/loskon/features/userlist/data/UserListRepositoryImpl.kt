package com.loskon.features.userlist.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.loskon.database.source.LocalDataSource
import com.loskon.features.model.UserModel
import com.loskon.features.model.toUserEntity
import com.loskon.features.model.toUserModel
import com.loskon.features.userlist.domain.UserListRepository
import com.loskon.network.source.NetworkPagingDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserListRepositoryImpl(
    private val networkPagingDataSource: NetworkPagingDataSource,
    private val localDataSource: LocalDataSource
) : UserListRepository {

    override suspend fun getUsers(): Flow<PagingData<UserModel>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { networkPagingDataSource }
        ).flow.map { pagingData -> pagingData.map { it.toUserModel() } }
    }

    override suspend fun getCachedUsers(): List<UserModel>? {
        return localDataSource.getCachedUsers()?.map { it.toUserModel() }
    }

    override suspend fun setUsers(users: List<UserModel>) {
        localDataSource.setUsers(users.map { it.toUserEntity() })
    }

    companion object {

        private const val PAGE_SIZE = 20
    }
}