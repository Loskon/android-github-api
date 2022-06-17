package com.loskon.githubapi.app.features.repositoryinfo

import com.loskon.githubapi.app.features.repositoryinfo.presentation.RepoInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repoInfoModule = module {

    viewModel { params -> RepoInfoViewModel(params[0]) }
}