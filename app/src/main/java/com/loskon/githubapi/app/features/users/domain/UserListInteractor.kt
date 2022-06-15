package com.loskon.githubapi.app.features.users.domain

import com.loskon.githubapi.network.retrofit.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserListInteractor(
    private val userListRepository: UserListRepository
) {

    suspend fun getUsersPairAsFlow(): Flow<Pair<Boolean, List<UserModel>>> {
        return userListRepository.getUsersPairAsFlow().map { pair ->
            pair.first to pair.second.filter { it.type == USER_TYPE }.sortedBy { it.id }
        }
    }

    companion object {
        private const val USER_TYPE = "User"
    }
}