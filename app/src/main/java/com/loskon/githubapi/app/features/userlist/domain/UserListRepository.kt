package com.loskon.githubapi.app.features.userlist.domain

import com.loskon.githubapi.network.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserListRepository {

    suspend fun getUsersAsFlow(pageSize: Int): Flow<List<UserModel>>
}