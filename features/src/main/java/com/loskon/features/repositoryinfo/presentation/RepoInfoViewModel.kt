package com.loskon.features.repositoryinfo.presentation

import com.loskon.base.presentation.viewmodel.BaseViewModel
import com.loskon.features.model.RepoModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RepoInfoViewModel : BaseViewModel() {

    private val repoInfoState = MutableStateFlow<RepoModel?>(null)
    val getRepoInfoState get() = repoInfoState.asStateFlow()

    fun setRepository(repository: RepoModel) {
        launchErrorJob {
            repoInfoState.emit(repository)
        }
    }
}