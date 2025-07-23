package com.loskon.features.repoinfo

import com.loskon.features.repoinfo.presentation.RepoInfoViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val repoInfoModule = module {
    viewModelOf(::RepoInfoViewModel)
}