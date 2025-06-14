package com.loskon.features.userlist.domain

import androidx.paging.PagingData
import com.loskon.features.model.UserModel
import kotlinx.coroutines.flow.Flow

class UserListInteractor(
    private val userListRepository: UserListRepository
) {

    suspend fun getUsers(): Flow<PagingData<UserModel>> {
        return userListRepository.getUsers()
    }

    suspend fun getCachedUsers(): List<UserModel>? {
        return userListRepository.getCachedUsers()
    }

    suspend fun setUsers(users: List<UserModel>) {
        userListRepository.setUsers(users)
    }
}