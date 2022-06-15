package com.loskon.githubapi.app.features.userprofile.presentation

import com.loskon.githubapi.app.features.userprofile.domain.UserProfileInteractor
import com.loskon.githubapi.base.presentation.BaseViewModel
import com.loskon.githubapi.network.retrofit.model.UserModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest

class UserProfileViewModel(
    private val username: String,
    private val userProfileInteractor: UserProfileInteractor
) : BaseViewModel() {

    private val user = MutableStateFlow<UserModel?>(null)
    val getUser get() = user.asStateFlow()

    init {
        launchErrorJob {
            userProfileInteractor.getUser(username).collectLatest { user.emit(it) }
        }
    }
}