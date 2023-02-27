package com.loskon.features.userprofile.presentation

import com.loskon.base.widget.recyclerview.RecyclerListDiffUtil
import com.loskon.features.model.RepositoryModel

val repoListDiffUtil = object : RecyclerListDiffUtil<RepositoryModel> {

    override fun areItemsTheSame(oldItem: RepositoryModel, newItem: RepositoryModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RepositoryModel, newItem: RepositoryModel): Boolean {
        return oldItem.fullName == newItem.fullName && oldItem.size == newItem.size
    }
}