package com.loskon.githubapi.domain.interactor

import com.loskon.githubapi.domain.repository.UserListRepository
import com.loskon.githubapi.domain.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserListInteractor(
    private val userListRepository: UserListRepository
) {

    suspend fun getUsersAsFlow(pageSize: Int): Flow<List<UserModel>> {
        return userListRepository.getUsersAsFlow(pageSize).map { users ->
            users.filter { it.type == USER_TYPE }.sortedBy { it.id }
        }
    }

    companion object {
        private const val USER_TYPE = "User"
    }
}