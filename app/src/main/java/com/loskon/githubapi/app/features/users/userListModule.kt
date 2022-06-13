package com.loskon.githubapi.app.features.users

import com.loskon.githubapi.app.features.users.data.UserListRepositoryImpl
import com.loskon.githubapi.app.features.users.domain.UserListInteractor
import com.loskon.githubapi.app.features.users.domain.UserListRepository
import com.loskon.githubapi.app.features.users.presentation.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userListModule = module {

    single<UserListRepository> { UserListRepositoryImpl(get()) }
    viewModel { UserListViewModel(get()) }
    factory { UserListInteractor(get()) }
}