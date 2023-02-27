package com.loskon.features.userlist.domain

import com.loskon.features.model.UserModel

interface UserListRepository {

    suspend fun getUsers(pageSize: Int, since: Int): List<UserModel>
}