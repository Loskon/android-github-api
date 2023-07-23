package com.loskon.features.repolist.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.loskon.features.model.UserModel
import com.loskon.features.model.toUserModel
import com.loskon.features.repolist.domain.RepoListRepository
import com.loskon.network.api.GithubApi
import com.loskon.network.dto.UserDto
import com.loskon.network.source.NetworkPagingDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepoListRepositoryImpl(
    private val networkDataSource: GithubApi
) : RepoListRepository {

    override fun getUsers(): Flow<PagingData<UserModel>> {
        return getUsers2().map { pagingData -> pagingData.map { it.toUserModel() } }
    }

    private fun getUsers2(): Flow<PagingData<UserDto>> {
        return Pager(
            config = PagingConfig(pageSize = NetworkPagingDataSource.NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { NetworkPagingDataSource(networkDataSource) }
        ).flow
    }
}