package com.loskon.features.userlist.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.loskon.base.extension.coroutines.onStart
import com.loskon.base.presentation.viewmodel.BaseViewModel
import com.loskon.features.userlist.domain.UserListInteractor
import com.loskon.features.util.network.ConnectionManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest

class UserListViewModel(
    private val userListInteractor: UserListInteractor,
    private val connectionManager: ConnectionManager
) : BaseViewModel() {

    private val userListState = MutableStateFlow<UserListState>(UserListState.Loading)
    val getUserListState get() = userListState.asStateFlow()

    private var job: Job? = null

    fun getUsers() {
        job?.cancel()
        job = launchErrorJob(
            errorBlock = { userListState.tryEmit(UserListState.Failure) }
        ) {
            if (connectionManager.hasConnected()) {
                val users = userListInteractor.getUsers().cachedIn(viewModelScope)
                users.collectLatest { userListState.emit(UserListState.Success(it)) }
            } else {
                userListState.emit(UserListState.ConnectionFailure(userListInteractor.getCachedUsers()))
            }
        }.onStart {
            userListState.tryEmit(UserListState.Loading)
        }
    }
}