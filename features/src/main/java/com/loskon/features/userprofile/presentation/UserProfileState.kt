package com.loskon.features.userprofile.presentation

sealed class UserProfileState {
    object Success : UserProfileState()
    object Loading : UserProfileState()
    object Failure : UserProfileState()
}