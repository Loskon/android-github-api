package com.loskon.features.userprofile

import com.loskon.features.userprofile.data.UserProfileRepositoryImpl
import com.loskon.features.userprofile.domain.UserProfileInteractor
import com.loskon.features.userprofile.domain.UserProfileRepository
import com.loskon.features.userprofile.presentation.UserProfileViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val userProfileModule = module {
    single<UserProfileRepository> { UserProfileRepositoryImpl(get(), get()) }
    viewModelOf(::UserProfileViewModel)
    factory { UserProfileInteractor(get()) }
}