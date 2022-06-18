package com.loskon.githubapi.app.features.repositoryinfo.presentation

import com.loskon.githubapi.base.presentation.viewmodel.BaseViewModel
import com.loskon.githubapi.network.retrofit.model.RepositoryModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RepoInfoViewModel(
    private val repository: RepositoryModel
) : BaseViewModel() {

    private val repoInfoState = MutableStateFlow<RepositoryModel?>(null)
    val getRepoInfoState get() = repoInfoState.asStateFlow()

    init {
        launchErrorJob {
            repoInfoState.emit(repository)
        }
    }
}