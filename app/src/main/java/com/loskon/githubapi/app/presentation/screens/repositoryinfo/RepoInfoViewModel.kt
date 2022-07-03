package com.loskon.githubapi.app.presentation.screens.repositoryinfo

import com.loskon.githubapi.app.base.presentation.viewmodel.BaseViewModel
import com.loskon.githubapi.domain.model.RepositoryModel
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