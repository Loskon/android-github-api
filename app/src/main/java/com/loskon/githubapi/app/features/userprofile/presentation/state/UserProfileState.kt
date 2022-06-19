package com.loskon.githubapi.app.features.userprofile.presentation.state

import com.loskon.githubapi.network.retrofit.model.UserModel

data class UserProfileState (
    val loading: Boolean = false,
    val fromCache: Boolean = false,
    val user: UserModel? = null
)