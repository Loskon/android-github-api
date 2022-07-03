package com.loskon.githubapi.di

import com.loskon.githubapi.app.presentation.screens.userprofile.UserProfileViewModel
import com.loskon.githubapi.data.repositoryimpl.UserProfileRepositoryImpl
import com.loskon.githubapi.domain.interactor.UserProfileInteractor
import com.loskon.githubapi.domain.repository.UserProfileRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userProfileModule = module {

    single<UserProfileRepository> { UserProfileRepositoryImpl(get()) }
    viewModel { UserProfileViewModel(get()) }
    factory { UserProfileInteractor(get()) }
}