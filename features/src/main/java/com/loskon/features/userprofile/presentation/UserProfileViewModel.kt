package com.loskon.features.userprofile.presentation

import com.loskon.base.presentation.viewmodel.BaseViewModel
import com.loskon.features.userprofile.domain.UserProfileInteractor
import com.loskon.features.util.connect.ConnectionManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserProfileViewModel(
    private val userProfileInteractor: UserProfileInteractor,
    private val connectionManager: ConnectionManager
) : BaseViewModel() {

    private val userProfileState = MutableStateFlow<UserProfileState>(UserProfileState.Loading)
    val userProfileStateFlow get() = userProfileState.asStateFlow()

    private var job: Job? = null

    fun getUser(login: String) {
        job?.cancel()
        job = launchErrorJob(
            startBlock = { userProfileState.tryEmit(UserProfileState.Loading) },
            errorBlock = { userProfileState.tryEmit(UserProfileState.Failure) },
        ) {
            if (connectionManager.hasConnect()) {
                val user = userProfileInteractor.getUser(login)
                userProfileInteractor.setUserInCache(user)
                userProfileState.emit(UserProfileState.Success(user))
            } else {
                val user = userProfileInteractor.getCachedUser(login)
                userProfileState.emit(UserProfileState.ConnectionFailure(user))
            }
        }
    }
}