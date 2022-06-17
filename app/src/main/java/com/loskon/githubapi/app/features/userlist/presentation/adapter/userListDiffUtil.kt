package com.loskon.githubapi.app.features.userlist.presentation.adapter

import com.loskon.githubapi.base.presentation.RecyclerListDiffUtil
import com.loskon.githubapi.network.retrofit.model.UserModel

val userListDiffUtil = object : RecyclerListDiffUtil<UserModel> {

    override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem.login == newItem.login
    }

    override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem.id == newItem.id && oldItem.nodeId == newItem.nodeId
    }
}