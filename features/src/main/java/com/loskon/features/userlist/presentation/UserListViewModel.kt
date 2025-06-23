package com.loskon.features.userlist.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.loskon.base.presentation.viewmodel.BaseViewModel
import com.loskon.features.userlist.domain.UserListInteractor
import com.loskon.features.util.network.ConnectManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

class UserListViewModel(
    private val userListInteractor: UserListInteractor,
    private val connectManager: ConnectManager
) : BaseViewModel() {

    private val userListState = MutableStateFlow<UserListState>(UserListState.Loading)
    val getUserListState get() = userListState.asStateFlow()

    private var job: Job? = null

    fun getUsers() {
        job?.cancel()
        job = launchErrorJob(
            startBlock = { userListState.tryEmit(UserListState.Loading) },
            errorBlock = { userListState.tryEmit(UserListState.Failure) }
        ) {
            val connect = connectManager.hasConnect()
            val users = userListInteractor.getUsers().cachedIn(viewModelScope)

            users.collectLatest {
                Timber.d("UserCollectLatest SET")
                val pp = it.map { ss->
                    Timber.d("UserCollectLatest: " + ss)
                    return@map ss
                }
                userListState.emit(
                UserListState.Success(pp, connect))
            }
        }
    }
}