package com.loskon.features.userlist.presentation

import com.loskon.base.extension.coroutines.onStart
import com.loskon.base.presentation.viewmodel.BaseViewModel
import com.loskon.features.userlist.domain.UserListInteractor
import com.loskon.features.util.network.ConnectionManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserListViewModel(
    private val userListInteractor: UserListInteractor,
    private val connectionManager: ConnectionManager
) : BaseViewModel() {

    private val userListState = MutableStateFlow<UserListState>(UserListState.Loading)
    val getUserListState get() = userListState.asStateFlow()

    private var job: Job? = null

    fun getUsers(perPage: Int, since: Int) {
        job?.cancel()
        job = launchErrorJob(
            errorBlock = { userListState.tryEmit(UserListState.Failure) }
        ) {
            if (connectionManager.hasConnected()) {
                userListState.emit(UserListState.Success(userListInteractor.getUsers(perPage, since)))
            } else {
                userListState.emit(UserListState.ConnectionFailure)
            }
        }.onStart {
            userListState.tryEmit(UserListState.Loading)
        }
    }
}