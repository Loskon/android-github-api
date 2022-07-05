package com.loskon.githubapi.domain.repository

import com.loskon.githubapi.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserListRepository {

    suspend fun getUsersAsFlow(pageSize: Int, since: Int): Flow<List<UserModel>>
}