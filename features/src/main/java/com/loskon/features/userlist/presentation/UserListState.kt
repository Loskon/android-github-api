package com.loskon.features.userlist.presentation

import com.loskon.features.model.UserModel

sealed class UserListState {
    data class Success(val users: List<UserModel>) : UserListState()
    object Loading : UserListState()
    object Failure : UserListState()
}