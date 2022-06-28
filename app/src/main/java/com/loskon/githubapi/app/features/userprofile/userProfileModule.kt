package com.loskon.githubapi.app.features.userprofile

import com.loskon.githubapi.app.features.userprofile.data.UserProfileRepositoryImpl
import com.loskon.githubapi.app.features.userprofile.domain.UserProfileInteractor
import com.loskon.githubapi.app.features.userprofile.domain.UserProfileRepository
import com.loskon.githubapi.app.features.userprofile.presentation.UserProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userProfileModule = module {

    single<UserProfileRepository> { UserProfileRepositoryImpl(get()) }
    viewModel { UserProfileViewModel(get()) }
    factory { UserProfileInteractor(get()) }
}