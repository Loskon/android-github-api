package com.loskon.githubapi.app.features.userprofile.presentation.adapter

import com.loskon.githubapi.base.presentation.RecyclerListDiffUtil
import com.loskon.githubapi.network.retrofit.model.RepositoryModel

val repoListDiffUtil = object : RecyclerListDiffUtil<RepositoryModel> {

    override fun areItemsTheSame(oldItem: RepositoryModel, newItem: RepositoryModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RepositoryModel, newItem: RepositoryModel): Boolean {
        return oldItem.fullName == newItem.fullName && oldItem.size == newItem.size
    }
}