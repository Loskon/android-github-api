package com.loskon.githubapi.app.features.users.domain

import com.loskon.githubapi.network.retrofit.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserListRepository {

    suspend fun getUsersAsFlow(): Flow<Pair<Boolean, List<UserModel>>>
}