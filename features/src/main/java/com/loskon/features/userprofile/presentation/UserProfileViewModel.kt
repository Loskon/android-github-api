package com.loskon.features.userprofile.presentation

import com.loskon.base.extension.coroutines.onStart
import com.loskon.base.presentation.viewmodel.BaseViewModel
import com.loskon.features.userprofile.domain.UserProfileInteractor
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserProfileViewModel(
    private val userProfileInteractor: UserProfileInteractor
) : BaseViewModel() {

    private val userProfileState = MutableStateFlow<UserProfileState>(UserProfileState.Loading)
    val getUserProfileState get() = userProfileState.asStateFlow()

    private var job: Job? = null

    fun getUser(username: String) {
        job?.cancel()
        job = launchErrorJob(
            errorBlock = { userProfileState.tryEmit(UserProfileState.Failure) }
        ) {
            userProfileState.emit(UserProfileState.Success(userProfileInteractor.getUser(username)))
        }.onStart {
            userProfileState.tryEmit(UserProfileState.Loading)
        }
    }
}