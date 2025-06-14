package com.loskon.features.userlist.domain

import androidx.paging.PagingData
import com.loskon.features.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserListRepository {

    suspend fun getUsers(): Flow<PagingData<UserModel>>

    suspend fun getCachedUsers(): List<UserModel>?

    suspend fun setUsers(users: List<UserModel>)
}