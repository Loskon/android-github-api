package com.loskon.githubapi.app.features.userlist.presentation.state

import com.loskon.githubapi.network.model.UserModel

data class UserListState(
    val loading: Boolean = false,
    val fromCache: Boolean = false,
    val users: List<UserModel>? = null
)