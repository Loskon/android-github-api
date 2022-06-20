package com.loskon.githubapi.app.features.userlist.domain

import com.loskon.githubapi.network.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserListInteractor(
    private val userListRepository: UserListRepository
) {

    suspend fun getUsersAsFlow(): Flow<List<UserModel>> {
        return userListRepository.getUsersAsFlow().map { users ->
            users.filter { it.type == USER_TYPE }.sortedBy { it.id }
        }
    }

    companion object {
        private const val USER_TYPE = "User"
    }
}