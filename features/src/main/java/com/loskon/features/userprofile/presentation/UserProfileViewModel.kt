package com.loskon.features.userprofile.presentation

import com.loskon.base.extension.coroutines.onStart
import com.loskon.base.presentation.viewmodel.BaseViewModel
import com.loskon.features.userprofile.domain.UserProfileInteractor
import com.loskon.features.util.network.ConnectionManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserProfileViewModel(
    private val userProfileInteractor: UserProfileInteractor,
    private val connectionManager: ConnectionManager
) : BaseViewModel() {

    private val userProfileState = MutableStateFlow<UserProfileState>(UserProfileState.Loading)
    val getUserProfileState get() = userProfileState.asStateFlow()

    private var job: Job? = null

    fun getUser(login: String) {
        job?.cancel()
        job = launchErrorJob(
            errorBlock = { userProfileState.tryEmit(UserProfileState.Failure) }
        ) {
            if (connectionManager.hasConnected()) {
                val user = userProfileInteractor.getUser(login)
                userProfileState.emit(UserProfileState.Success(user))
                userProfileInteractor.setUser(user)
            } else {
                userProfileState.emit(UserProfileState.ConnectionFailure(userProfileInteractor.getCachedUser(login)))
            }
        }.onStart {
            userProfileState.tryEmit(UserProfileState.Loading)
        }
    }
}