package com.loskon.githubapi.app.features.users.presentation

import com.loskon.githubapi.app.features.users.domain.UserListInteractor
import com.loskon.githubapi.app.features.users.presentation.state.ErrorType
import com.loskon.githubapi.app.features.users.presentation.state.UserListUiAction
import com.loskon.githubapi.base.presentation.BaseViewModel
import com.loskon.githubapi.network.retrofit.exception.EmptyCacheException
import com.loskon.githubapi.network.retrofit.exception.NoSuccessfulException
import com.loskon.githubapi.network.retrofit.model.UserModel
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

    private var job: Job? = null

    init {
        getUsersAsFlow()
    }

    private fun getUsersAsFlow() {
        job?.cancel()
        job = launchErrorJob(onErrorBlock = { handleErrors(it) }) {
            userListInteractor.getUsersAsFlow().collectLatest { setMainState(it) }
        }
    }

    private fun handleErrors(throwable: Throwable) {
        when (throwable) {
            is EmptyCacheException -> emitAction(UserListUiAction.ShowError(ErrorType.EMPTY_CACHE))
            is NoSuccessfulException -> emitAction(UserListUiAction.ShowError(ErrorType.NO_SUCCESSFUL, throwable.message))
            else -> emitAction(UserListUiAction.ShowError(ErrorType.OTHER, throwable.message))
        }
    }

    private fun emitAction(action: UserListUiAction) {
        usersAction.tryEmit(action)
    }

    private suspend fun setMainState(first: Pair<Boolean, List<UserModel>>) {
        usersState.emit(first.second)

        if (first.first) {
            emitAction(UserListUiAction.ShowError(ErrorType.EMPTY_CACHE))
        } else {
            emitAction(UserListUiAction.ShowLoadingIndicator(false))
        }
    }

    fun performRepeatRequest() {
        usersAction.tryEmit(UserListUiAction.ShowLoadingIndicator(true))
        getUsersAsFlow()
    }
}