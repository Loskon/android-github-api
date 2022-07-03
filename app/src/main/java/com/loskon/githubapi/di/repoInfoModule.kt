package com.loskon.githubapi.app.presentation.screens.repositoryinfo

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repoInfoModule = module {

    viewModel { params -> RepoInfoViewModel(params[0]) }
}