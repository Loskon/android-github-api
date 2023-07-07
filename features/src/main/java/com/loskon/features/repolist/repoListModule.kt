package com.loskon.features.repolist

import com.loskon.features.repolist.data.RepoListRepositoryImpl
import com.loskon.features.repolist.domain.RepoListInteractor
import com.loskon.features.repolist.domain.RepoListRepository
import com.loskon.features.repolist.presentation.RepoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repoListModule = module {
    single<RepoListRepository> { RepoListRepositoryImpl(get()) }
    viewModel { RepoListViewModel(get(), get()) }
    factory { RepoListInteractor(get()) }
}