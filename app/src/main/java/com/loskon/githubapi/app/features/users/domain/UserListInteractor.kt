package com.loskon.githubapi.app.features.users.domain

import com.loskon.githubapi.network.retrofit.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

class UserListInteractor(
    private val userListRepository: UserListRepository
) {

    suspend fun getUsersAsFlow(): Flow<Pair<Boolean, List<UserModel>>> {
        return userListRepository.getUsersAsFlow()
    }
}