package com.loskon.githubapi.app.features.userprofile.presentation

import com.loskon.githubapi.app.features.userprofile.domain.UserProfileInteractor
import com.loskon.githubapi.base.presentation.BaseViewModel
import com.loskon.githubapi.network.retrofit.model.UserModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest

class UserProfileViewModel(
    private val userProfileInteractor: UserProfileInteractor,
    private val username: String
) : BaseViewModel() {

    private val userProfileState = MutableStateFlow<UserModel?>(null)
    val getUserProfileState get() = userProfileState.asStateFlow()

    init {
        launchErrorJob {
            userProfileInteractor.getUser(username).collectLatest { userProfileState.emit(it) }
        }
    }
}