package com.loskon.githubapi.di

import com.loskon.githubapi.app.presentation.screens.userlist.UserListViewModel
import com.loskon.githubapi.data.repositoryimpl.UserListRepositoryImpl
import com.loskon.githubapi.domain.interactor.UserListInteractor
import com.loskon.githubapi.domain.repository.UserListRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userListModule = module {

    single<UserListRepository> { UserListRepositoryImpl(get()) }
    viewModel { UserListViewModel(get()) }
    factory { UserListInteractor(get()) }
}