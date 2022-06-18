package com.loskon.githubapi.app.features.userlist.presentation

import com.loskon.githubapi.app.features.userlist.domain.UserListInteractor
import com.loskon.githubapi.app.features.userlist.presentation.state.ErrorTypeUserList
import com.loskon.githubapi.app.features.userlist.presentation.state.UserListAction
import com.loskon.githubapi.base.presentation.viewmodel.BaseViewModel
import com.loskon.githubapi.network.retrofit.exception.EmptyCacheException
import com.loskon.githubapi.network.retrofit.exception.NoSuccessfulException
import com.loskon.githubapi.network.retrofit.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class UserListViewModel(
    private val userListInteractor: UserListInteractor
) : BaseViewModel() {

    private val userListState = MutableStateFlow<List<UserModel>?>(null)
    val getUsersState get() = userListState.asStateFlow()

    private val userListAction = MutableStateFlow<UserListAction>(UserListAction.ShowLoadingIndicator(true))
    val geuUsersAction get() = userListAction.asStateFlow()

    private var job: Job? = null

    init {
        performUsersRequest()
    }

    fun performUsersRequest() {
        emitAction(UserListAction.ShowLoadingIndicator(true))
        getUsersAsFlow()
    }

    private fun emitAction(action: UserListAction) {
        userListAction.tryEmit(action)
    }

    private fun getUsersAsFlow() {
        job?.cancel()
        job = launchErrorJob(dispatcher = Dispatchers.IO, onErrorBlock = { handleErrors(it) }) {
            userListInteractor.getUsersAsFlow().collectLatest { setUserListState(it) }
        }
    }

    private fun handleErrors(throwable: Throwable) {
        when (throwable) {
            is EmptyCacheException -> emitAction(UserListAction.ShowError(ErrorTypeUserList.EMPTY_CACHE))
            is NoSuccessfulException -> emitAction(UserListAction.ShowError(ErrorTypeUserList.NO_SUCCESSFUL, throwable.message))
            is UnknownHostException -> emitAction(UserListAction.ShowError(ErrorTypeUserList.TIMEOUT))
            is SocketTimeoutException -> emitAction(UserListAction.ShowError(ErrorTypeUserList.UNKNOWN_HOST))
            else -> emitAction(UserListAction.ShowError(ErrorTypeUserList.OTHER, throwable.message))
        }
    }

    private suspend fun setUserListState(users: List<UserModel>) {
        userListState.emit(users)
        selectActionForDataFromCache(users.first().fromCache)
    }

    private fun selectActionForDataFromCache(fromCache: Boolean) {
        if (fromCache) {
            emitAction(UserListAction.ShowError(ErrorTypeUserList.EMPTY_CACHE))
        } else {
            emitAction(UserListAction.ShowLoadingIndicator(false))
        }
    }
}