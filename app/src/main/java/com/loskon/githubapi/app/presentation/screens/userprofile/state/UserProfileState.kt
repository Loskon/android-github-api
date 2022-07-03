package com.loskon.githubapi.app.presentation.screens.userprofile.state

import com.loskon.githubapi.domain.model.UserModel

data class UserProfileState(
    val loading: Boolean = false,
    val fromCache: Boolean = false,
    val user: UserModel? = null
)