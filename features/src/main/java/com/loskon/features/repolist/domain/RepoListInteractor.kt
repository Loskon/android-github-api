package com.loskon.features.repolist.domain

import androidx.paging.PagingData
import com.loskon.features.model.UserModel
import kotlinx.coroutines.flow.Flow

class RepoListInteractor(
    private val repoListRepository: RepoListRepository
) {

    fun getUsers(): Flow<PagingData<UserModel>> {
        return repoListRepository.getUsers()
    }
}