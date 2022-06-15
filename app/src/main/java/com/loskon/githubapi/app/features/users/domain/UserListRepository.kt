package com.loskon.githubapi.app.features.users.domain

import com.loskon.githubapi.network.retrofit.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserListRepository {

    suspend fun getUsersPairAsFlow(): Flow<Pair<Boolean, List<UserModel>>>
}