package com.loskon.githubapi.app.presentation.screens.userlist.state

import com.loskon.githubapi.domain.model.UserModel

data class UserListState(
    val loading: Boolean = false,
    val fromCache: Boolean = false,
    val users: List<UserModel>? = null
)