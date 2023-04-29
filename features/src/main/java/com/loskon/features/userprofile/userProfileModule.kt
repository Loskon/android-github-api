package com.loskon.features.userprofile

import com.loskon.features.userprofile.data.UserProfileRepositoryImpl
import com.loskon.features.userprofile.domain.UserProfileInteractor
import com.loskon.features.userprofile.domain.UserProfileRepository
import com.loskon.features.userprofile.presentation.UserProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userProfileModule = module {
    single<UserProfileRepository> { UserProfileRepositoryImpl(get()) }
    viewModel { UserProfileViewModel(get(), get()) }
    factory { UserProfileInteractor(get()) }
}