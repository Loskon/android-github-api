package com.loskon.features.userprofile.presentation

import com.loskon.features.model.UserModel

sealed class UserProfileState {
    data class Success(val user: UserModel) : UserProfileState()
    object Loading : UserProfileState()
    object Failure : UserProfileState()
}