package com.loskon.githubapi.app.features.userprofile.presentation

import com.loskon.githubapi.app.features.userprofile.domain.UserProfileInteractor
import com.loskon.githubapi.app.features.userprofile.presentation.state.UserProfileState
import com.loskon.githubapi.base.presentation.viewmodel.IOErrorViewModel
import com.loskon.githubapi.network.model.UserModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest

class UserProfileViewModel(
    private val userProfileInteractor: UserProfileInteractor,
    private val username: String
) : IOErrorViewModel() {

    private val userProfileState = MutableStateFlow(UserProfileState())
    val getUserProfileState get() = userProfileState.asStateFlow()

    private var job: Job? = null

    init {
        performUserRequest()
    }

    fun performUserRequest() {
        setLoadingStatus(true)
        getUsersAsFlow()
    }

    private fun setLoadingStatus(loading: Boolean) {
        userProfileState.tryEmit(
            userProfileState.value.copy(loading = loading)
        )
    }

    private fun getUsersAsFlow() {
        job?.cancel()
        job = launchIOErrorJob(errorFunction = { setLoadingStatus(false) }) {
            userProfileInteractor.getUser(username).collectLatest { setUserListState(it) }
        }
    }

    private suspend fun setUserListState(user: UserModel) {
        userProfileState.emit(
            UserProfileState(
                loading = false,
                fromCache = user.fromCache,
                user = user
            )
        )
    }
}