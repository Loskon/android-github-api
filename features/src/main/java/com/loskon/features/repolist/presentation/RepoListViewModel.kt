package com.loskon.features.repolist.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.loskon.base.presentation.viewmodel.BaseViewModel
import com.loskon.features.model.UserModel
import com.loskon.features.repolist.domain.RepoListInteractor
import com.loskon.features.util.network.ConnectionManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RepoListViewModel(
    private val userListInteractor: RepoListInteractor,
    private val connectionManager: ConnectionManager
) : BaseViewModel() {

    private val repoListState = MutableStateFlow<RepoListAction?>(null)
    val getUserListAction get() = repoListState.asStateFlow()
    val getUsers get(): Flow<PagingData<UserModel>> = userListInteractor.getUsers().cachedIn(viewModelScope)

    private var job: Job? = null

    fun checkInternet() {
        launchErrorJob {
            if (connectionManager.hasConnected()) {
                repoListState.emit(RepoListAction.Failure)
            } else {
                repoListState.emit(RepoListAction.ConnectionFailure)
            }
        }
    }

    fun refreshUsers() {
        job?.cancel()
        job = launchErrorJob {
            if (connectionManager.hasConnected()) {
                repoListState.emit(RepoListAction.Refresh)
            } else {
                repoListState.emit(RepoListAction.ConnectionFailure)
            }
        }
    }

    /*private val repoListState = MutableStateFlow<RepoListState>(RepoListState.Loading)
    val getUserListState get() = repoListState.asStateFlow()

    private var job: Job? = null

    fun getUsers() {
      job?.cancel()
      job = launchErrorJob(
          errorBlock = { repoListState.tryEmit(RepoListState.Failure) }
      ) {
          if (connectionManager.hasConnected()) {
              userListInteractor.getUsers().collect { repoListState.emit(RepoListState.Success(it)) }
          } else {
              repoListState.emit(RepoListState.ConnectionFailure)
          }
      }.onStart {
          repoListState.tryEmit(RepoListState.Loading)
      }
    }

    fun refreshUsers() {
      job?.cancel()
      job = launchErrorJob(
          errorBlock = { repoListState.tryEmit(RepoListState.Failure) }
      ) {
          if (connectionManager.hasConnected()) {
              repoListState.tryEmit(RepoListState.ConnectionFailure)
          } else {
              repoListState.tryEmit(RepoListState.ConnectionFailure)
          }
      }.onStart {
          repoListState.tryEmit(RepoListState.Loading)
      }
    }*/
}