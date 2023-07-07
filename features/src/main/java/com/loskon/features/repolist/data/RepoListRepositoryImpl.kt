package com.loskon.features.repolist.data

import androidx.paging.PagingData
import androidx.paging.map
import com.loskon.features.model.UserModel
import com.loskon.features.model.toUserModel
import com.loskon.features.repolist.domain.RepoListRepository
import com.loskon.network.source.NetworkPagingDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepoListRepositoryImpl(
    private val networkDataSource: NetworkPagingDataSource
) : RepoListRepository {

    override fun getUsers(): Flow<PagingData<UserModel>> {
        return networkDataSource.getUsers().map { pagingData -> pagingData.map { it.toUserModel() } }
    }
}