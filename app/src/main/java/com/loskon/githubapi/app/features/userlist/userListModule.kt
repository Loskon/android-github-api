package com.loskon.githubapi.app.features.userlist

import com.loskon.githubapi.app.features.userlist.data.UserListRepositoryImpl
import com.loskon.githubapi.app.features.userlist.domain.UserListInteractor
import com.loskon.githubapi.app.features.userlist.domain.UserListRepository
import com.loskon.githubapi.app.features.userlist.presentation.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userListModule = module {

    single<UserListRepository> { UserListRepositoryImpl(get()) }
    viewModel { params -> UserListViewModel(get(), params[0]) }
    factory { UserListInteractor(get()) }
}