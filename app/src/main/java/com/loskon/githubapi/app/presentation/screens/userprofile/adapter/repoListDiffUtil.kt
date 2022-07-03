package com.loskon.githubapi.app.presentation.screens.userprofile.adapter

import com.loskon.githubapi.app.base.widget.recyclerview.RecyclerListDiffUtil
import com.loskon.githubapi.domain.model.RepositoryModel

val repoListDiffUtil = object : RecyclerListDiffUtil<RepositoryModel> {

    override fun areItemsTheSame(oldItem: RepositoryModel, newItem: RepositoryModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RepositoryModel, newItem: RepositoryModel): Boolean {
        return oldItem.fullName == newItem.fullName && oldItem.size == newItem.size
    }
}