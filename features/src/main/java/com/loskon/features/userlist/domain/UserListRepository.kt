package com.loskon.features.userlist.domain

import androidx.paging.PagingData
import com.loskon.features.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserListRepository {

    fun getUsers(): Flow<PagingData<UserModel>>

    suspend fun getCachedUsers(): List<UserModel>?

    suspend fun setUsersInCache(users: List<UserModel>)
}