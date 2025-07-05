package com.loskon.features.userlist.presentation

import com.loskon.base.presentation.viewmodel.BaseViewModel
import com.loskon.features.userlist.domain.UserListInteractor
import com.loskon.features.util.connect.ConnectionManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserListViewModel(
    private val userListInteractor: UserListInteractor,
    private val connectionManager: ConnectionManager
) : BaseViewModel() {

    private val userListState = MutableStateFlow<UserListState>(UserListState.Loading)
    val userListStateFlow get() = userListState.asStateFlow()

    private var job: Job? = null

    fun getUsers() {
        job?.cancel()
        job = launchErrorJob(
            startBlock = { userListState.tryEmit(UserListState.Loading) },
            errorBlock = { userListState.tryEmit(UserListState.Failure) }
        ) {
            if (connectionManager.hasConnect()) {
                val users = userListInteractor.getUsers()
                userListInteractor.setUsersInCache(users)
                userListState.emit(UserListState.Success(users))
            } else {
                val users = userListInteractor.getCachedUsers()
                userListState.emit(UserListState.ConnectionFailure(users))
            }
        }
    }
}