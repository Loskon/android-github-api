package com.loskon.githubapi.app.features.users.domain

import com.loskon.githubapi.network.retrofit.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserListInteractor(
    private val userListRepository: UserListRepository
) {

    suspend fun getUsersAsFlow(): Flow<Pair<Boolean, List<UserModel>>> {
        return userListRepository.getUsersAsFlow().map { pair ->
            pair.first to pair.second.filter { it.type == USER_TYPE }
        }
    }

    companion object {
        private const val USER_TYPE = "User"
    }
}