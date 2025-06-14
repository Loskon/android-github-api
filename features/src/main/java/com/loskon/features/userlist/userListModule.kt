package com.loskon.features.userlist

import com.loskon.features.userlist.data.UserListRepositoryImpl
import com.loskon.features.userlist.domain.UserListInteractor
import com.loskon.features.userlist.domain.UserListRepository
import com.loskon.features.userlist.presentation.UserListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val userListModule = module {
    single<UserListRepository> { UserListRepositoryImpl(get(), get()) }
    viewModelOf(::UserListViewModel)
    factory { UserListInteractor(get()) }
}