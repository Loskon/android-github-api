package com.loskon.githubapi.app.presentation.screens.userprofile

import com.loskon.githubapi.app.base.presentation.viewmodel.IOErrorViewModel
import com.loskon.githubapi.app.presentation.screens.userprofile.state.UserProfileState
import com.loskon.githubapi.domain.interactor.UserProfileInteractor
import com.loskon.githubapi.domain.model.UserModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest

class UserProfileViewModel(
    private val userProfileInteractor: UserProfileInteractor
) : IOErrorViewModel() {

    private val userProfileState = MutableStateFlow(UserProfileState())
    val getUserProfileState get() = userProfileState.asStateFlow()

    private var job: Job? = null

    fun performUserRequest(username: String) {
        setLoadingStatus(true)
        getUsersAsFlow(username)
    }

    private fun setLoadingStatus(loading: Boolean) {
        userProfileState.tryEmit(
            userProfileState.value.copy(loading = loading)
        )
    }

    private fun getUsersAsFlow(username: String) {
        job?.cancel()
        job = launchIOErrorJob(errorBlock = { setLoadingStatus(false) }) {
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