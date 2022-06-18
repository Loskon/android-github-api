package com.loskon.githubapi.app.features.userprofile.presentation

import com.loskon.githubapi.app.features.userprofile.domain.UserProfileInteractor
import com.loskon.githubapi.app.features.userprofile.presentation.state.UserProfileLoadingStatus
import com.loskon.githubapi.base.presentation.viewmodel.IOErrorViewModel
import com.loskon.githubapi.network.retrofit.model.UserModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest

class UserProfileViewModel(
    private val userProfileInteractor: UserProfileInteractor,
    private val username: String
) : IOErrorViewModel() {

    private val userModel = MutableStateFlow<UserModel?>(null)
    val getUserModel get() = userModel.asStateFlow()

    private val loadingStatus = MutableSharedFlow<UserProfileLoadingStatus?>(replay = 1)
    val getLoadingStatus get() = loadingStatus.asSharedFlow()

    private var job: Job? = null

    init {
        performUserRequest()
    }

    fun performUserRequest() {
        tryEmitLoadingState(true)
        getUsersAsFlow()
    }

    private fun tryEmitLoadingState(loading: Boolean) {
        loadingStatus.tryEmit(UserProfileLoadingStatus(loading))
    }

    private fun getUsersAsFlow() {
        job?.cancel()
        job = launchIOErrorJob {
            userProfileInteractor.getUser(username).collectLatest { setUserListState(it) }
        }
    }

    private suspend fun setUserListState(user: UserModel) {
        userModel.emit(user)
        selectActionForDataFromCache(user.fromCache)
    }

    private fun selectActionForDataFromCache(fromCache: Boolean) {
        if (fromCache) {
            tryEmitIOErrorEmptyCache()
        } else {
            tryEmitLoadingState(false)
        }
    }
}