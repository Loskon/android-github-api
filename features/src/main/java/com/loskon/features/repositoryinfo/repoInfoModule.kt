package com.loskon.features.repositoryinfo

import com.loskon.features.repositoryinfo.presentation.RepoInfoViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val repoInfoModule = module {
    viewModelOf(::RepoInfoViewModel)
}