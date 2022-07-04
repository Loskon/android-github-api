package com.loskon.githubapi.di

import com.loskon.githubapi.app.presentation.screens.repositoryinfo.RepoInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repoInfoModule = module {

    viewModel { params -> RepoInfoViewModel(params[0]) }
}