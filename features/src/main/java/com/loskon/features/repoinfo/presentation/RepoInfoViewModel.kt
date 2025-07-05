package com.loskon.features.repoinfo.presentation

import com.loskon.base.presentation.viewmodel.BaseViewModel
import com.loskon.features.model.RepoModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RepoInfoViewModel : BaseViewModel() {

    private val repoInfoState = MutableStateFlow<RepoModel?>(null)
    val getRepoInfoState get() = repoInfoState.asStateFlow()

    fun getCachedRepo(repo: RepoModel) {
        launchErrorJob {
            repoInfoState.emit(repo)
        }
    }
}