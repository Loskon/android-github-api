package com.loskon.githubapi.app.features.userlist.presentation

import com.loskon.githubapi.app.features.userlist.domain.UserListInteractor
import com.loskon.githubapi.app.features.userlist.presentation.state.UserListState
import com.loskon.githubapi.base.presentation.viewmodel.IOErrorViewModel
import com.loskon.githubapi.network.model.UserModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest

class UserListViewModel(
    private val userListInteractor: UserListInteractor
) : IOErrorViewModel() {

    private val userListState = MutableStateFlow(UserListState())
    val getUserListState get() = userListState.asStateFlow()

    private var job: Job? = null

    init {
        performUsersRequest()
    }

    fun performUsersRequest() {
        setLoadingStatus(true)
        getUsersAsFlow()
    }

    private fun setLoadingStatus(loading: Boolean) {
        userListState.tryEmit(
            userListState.value.copy(loading = loading)
        )
    }

    private fun getUsersAsFlow() {
        job?.cancel()
        job = launchIOErrorJob(errorFunction = { setLoadingStatus(false) }) {
            userListInteractor.getUsersAsFlow().collectLatest { setUserListState(it) }
        }
    }

    private suspend fun setUserListState(users: List<UserModel>) {
        userListState.emit(
            UserListState(
                loading = false,
                fromCache = users.first().fromCache,
                users = users
            )
        )
    }
}