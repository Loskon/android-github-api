package com.loskon.features.userlist.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.loskon.base.presentation.viewmodel.BaseViewModel
import com.loskon.features.userlist.domain.UserListInteractor
import com.loskon.features.util.connect.ConnectManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest

class UserListViewModel(
    private val userListInteractor: UserListInteractor,
    private val connectManager: ConnectManager
) : BaseViewModel() {

    private val userListState = MutableStateFlow<UserListState>(UserListState.Loading)
    val userListStateFlow get() = userListState.asStateFlow()

    private val queryFlow = MutableStateFlow("")

    private var job: Job? = null

    val usersFlow = queryFlow.debounce(500).distinctUntilChanged().flatMapLatest { userListInteractor.getUsers().cachedIn(viewModelScope) }

    fun getUsers() {
        /*job?.cancel()
        job = launchErrorJob(
            startBlock = { userListState.tryEmit(UserListState.Loading) },
            errorBlock = { userListState.tryEmit(UserListState.Failure) }
        ) {
            val connect = connectManager.hasConnect()
            val users = userListInteractor.getUsers()

            //val tt = users.distinctUntilChanged().flatMapLatest { value -> flowOf(value) }

            users.distinctUntilChanged().cachedIn(viewModelScope).collectLatest {
                Timber.d("UserCollectLatest SET")
                val pp = it.map { ss ->
                    Timber.d("UserCollectLatest: %s", ss)
                    return@map ss
                }
                userListState.emit(
                    UserListState.Success(pp, connect)
                )
            }
        }*/
    }
}