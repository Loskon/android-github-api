package com.loskon.features.repolist.domain

import androidx.paging.PagingData
import com.loskon.features.model.UserModel
import kotlinx.coroutines.flow.Flow

interface RepoListRepository {

    fun getUsers(): Flow<PagingData<UserModel>>
}