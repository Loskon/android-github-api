package com.loskon.features.userlist.presentation

import androidx.paging.PagingData
import com.loskon.features.model.UserModel

sealed class UserListState {
    data class Success(val users: PagingData<UserModel>) : UserListState()
    object Loading : UserListState()
    object Failure : UserListState()
    data class ConnectionFailure(val users: List<UserModel>?) : UserListState()
}