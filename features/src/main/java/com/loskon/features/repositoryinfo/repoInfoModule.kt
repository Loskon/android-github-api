package com.loskon.features.repositoryinfo

import com.loskon.features.repositoryinfo.presentation.RepoInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repoInfoModule = module {
    viewModel { RepoInfoViewModel() }
}