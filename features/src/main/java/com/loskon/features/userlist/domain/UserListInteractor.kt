package com.loskon.features.userlist.domain

import com.loskon.features.model.UserModel

class UserListInteractor(
    private val userListRepository: UserListRepository
) {

    suspend fun getUsers(pageSize: Int, since: Int): List<UserModel> {
        return userListRepository.getUsers(pageSize, since).filter { it.type == USER_TYPE }.sortedBy { it.id }
    }

    suspend fun getCachedUsers(): List<UserModel>? {
        return userListRepository.getCachedUsers()
    }

    suspend fun setUsers(users: List<UserModel>) {
        userListRepository.setUsers(users)
    }

    companion object {
        private const val USER_TYPE = "User"
    }
}