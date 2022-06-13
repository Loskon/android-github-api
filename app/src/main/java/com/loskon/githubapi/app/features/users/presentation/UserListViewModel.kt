package com.loskon.githubapi.app.features.users.presentation

import com.loskon.githubapi.app.features.users.domain.UserListInteractor
import com.loskon.githubapi.app.features.users.presentation.state.ErrorType
import com.loskon.githubapi.app.features.users.presentation.state.UserListUiAction
import com.loskon.githubapi.base.presentation.BaseViewModel
import com.loskon.githubapi.network.retrofit.data.exception.EmptyCacheException
import com.loskon.githubapi.network.retrofit.data.exception.NoSuccessfulException
import com.loskon.githubapi.network.retrofit.domain.model.UserModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest

class UserListViewModel(
    private val userListInteractor: UserListInteractor
) : BaseViewModel() {

    private val usersState = MutableStateFlow<List<UserModel>?>(null)
    val getUsersState get() = usersState.asStateFlow()

    private val usersAction = MutableStateFlow<UserListUiAction>(UserListUiAction.ShowLoadingIndicator(true))
    val geuUsersAction get() = usersAction.asStateFlow()

    private var stateJob: Job? = null

    init {
        getUsersAsFlow()
    }

    private fun getUsersAsFlow() {
        stateJob?.cancel()
        stateJob = launchErrorJob(onErrorBlock = { handleErrors(it) }) {
            userListInteractor.getUsersAsFlow().collectLatest { setMainState(it) }
        }
    }

    private fun handleErrors(throwable: Throwable) {
        when (throwable) {
            is EmptyCacheException -> usersAction.tryEmit(UserListUiAction.ShowError(ErrorType.EMPTY_CACHE))
            is NoSuccessfulException -> usersAction.tryEmit(UserListUiAction.ShowError(ErrorType.NO_SUCCESSFUL))
            else -> usersAction.tryEmit(UserListUiAction.ShowError(ErrorType.UNKNOWN))
        }
    }

    private suspend fun setMainState(first: Pair<Boolean, List<UserModel>>) {
        usersState.emit(first.second)

        if (first.first) {
            usersAction.tryEmit(UserListUiAction.ShowError(ErrorType.EMPTY_CACHE))
        } else {
            usersAction.tryEmit(UserListUiAction.ShowLoadingIndicator(false))
        }
    }

    fun performRepeatRequest() {
        usersAction.tryEmit(UserListUiAction.ShowLoadingIndicator(true))
        getUsersAsFlow()
    }
}