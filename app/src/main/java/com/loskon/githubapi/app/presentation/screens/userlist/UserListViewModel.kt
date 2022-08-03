package com.loskon.githubapi.app.presentation.screens.userlist

import com.loskon.githubapi.app.base.presentation.viewmodel.IOErrorViewModel
import com.loskon.githubapi.app.presentation.screens.userlist.state.UserListState
import com.loskon.githubapi.domain.interactor.UserListInteractor
import com.loskon.githubapi.domain.model.UserModel
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

    fun performUsersRequest(perPage: Int, since: Int) {
        job?.cancel()
        job = launchIOErrorJob(errorBlock = { disableLoading() }) {
            userListState.emit(userListState.value.copy(loading = true))
            userListInteractor.getUsersAsFlow(perPage, since).collectLatest { setUserListState(it) }
        }
    }

    private fun disableLoading() {
        userListState.tryEmit(userListState.value.copy(loading = false))
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