package com.loskon.features.userlist.domain

import com.loskon.features.model.UserModel

class UserListInteractor(
    private val userListRepository: UserListRepository
) {

    suspend fun getUsers(): List<UserModel> {
        return userListRepository.getUsers().sortedBy { it.id }
    }

    suspend fun getCachedUsers(): List<UserModel>? {
        return userListRepository.getCachedUsers()
    }

    suspend fun setUsersInCache(users: List<UserModel>) {
        userListRepository.setUsersInCache(users)
    }
}