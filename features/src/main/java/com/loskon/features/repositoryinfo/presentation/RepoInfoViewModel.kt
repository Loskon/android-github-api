package com.loskon.features.repositoryinfo.presentation

import com.loskon.base.presentation.viewmodel.BaseViewModel
import com.loskon.features.model.RepositoryModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RepoInfoViewModel : BaseViewModel() {

    private val repoInfoState = MutableStateFlow<RepositoryModel?>(null)
    val getRepoInfoState get() = repoInfoState.asStateFlow()

    fun setRepository(repository: RepositoryModel) {
        launchErrorJob {
            repoInfoState.emit(repository)
        }
    }
}