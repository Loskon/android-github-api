package com.loskon.features.userprofile.presentation

import com.loskon.base.extension.coroutines.onStart
import com.loskon.base.presentation.viewmodel.BaseViewModel
import com.loskon.features.model.UserModel
import com.loskon.features.userprofile.domain.UserProfileInteractor
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserProfileViewModel(
    private val userProfileInteractor: UserProfileInteractor
) : BaseViewModel() {

    private val userProfileState = MutableStateFlow<UserProfileState>(UserProfileState.Loading)
    private val userProfileList = MutableStateFlow(UserModel())
    val getUserProfileState get() = userProfileState.asStateFlow()
    val getUserProfileList get() = userProfileList.asStateFlow()

    private var job: Job? = null

    fun getUser(username: String) {
        job?.cancel()
        job = launchErrorJob(
            errorBlock = { userProfileState.tryEmit(UserProfileState.Failure) }
        ) {
            userProfileList.emit(userProfileInteractor.getUser(username))
            userProfileState.tryEmit(UserProfileState.Success)
        }.onStart {
            userProfileState.tryEmit(UserProfileState.Loading)
        }
    }
}