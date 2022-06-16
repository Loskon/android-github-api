package com.loskon.githubapi.app.features.userlist.presentation

import com.loskon.githubapi.app.features.userlist.domain.UserListInteractor
import com.loskon.githubapi.app.features.userlist.presentation.state.ErrorType
import com.loskon.githubapi.app.features.userlist.presentation.state.UserListAction
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

    private val usersAction = MutableStateFlow<UserListAction>(UserListAction.ShowLoadingIndicator(true))
    val geuUsersAction get() = usersAction.asStateFlow()

    private var job: Job? = null

    init {
        getUsersAsFlow()
    }

    private fun getUsersAsFlow() {
        job?.cancel()
        job = launchErrorJob(onErrorBlock = { handleErrors(it) }) {
            userListInteractor.getUsersPairAsFlow().collectLatest { setMainState(it) }
        }
    }

    private fun handleErrors(throwable: Throwable) {
        when (throwable) {
            is EmptyCacheException -> emitAction(UserListAction.ShowError(ErrorType.EMPTY_CACHE))
            is NoSuccessfulException -> emitAction(UserListAction.ShowError(ErrorType.NO_SUCCESSFUL, throwable.message))
            else -> emitAction(UserListAction.ShowError(ErrorType.OTHER, throwable.message))
        }
    }

    private fun emitAction(action: UserListAction) {
        usersAction.tryEmit(action)
    }

    private suspend fun setMainState(first: Pair<Boolean, List<UserModel>>) {
        usersState.emit(first.second)

        if (first.first) {
            emitAction(UserListAction.ShowError(ErrorType.EMPTY_CACHE))
        } else {
            emitAction(UserListAction.ShowLoadingIndicator(false))
        }
    }

    fun performRepeatRequest() {
        usersAction.tryEmit(UserListAction.ShowLoadingIndicator(true))
        getUsersAsFlow()
    }
}