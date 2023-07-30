package com.loskon.features.userlist.domain

import com.loskon.features.model.UserModel

interface UserListRepository {

    suspend fun getUsers(): List<UserModel>

    suspend fun getCachedUsers(): List<UserModel>?

    suspend fun setUsers(users: List<UserModel>)
}