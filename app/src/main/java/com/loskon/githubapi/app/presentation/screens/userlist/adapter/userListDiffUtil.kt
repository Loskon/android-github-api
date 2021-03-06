package com.loskon.githubapi.app.presentation.screens.userlist.adapter

import com.loskon.githubapi.app.base.widget.recyclerview.RecyclerListDiffUtil
import com.loskon.githubapi.domain.model.UserModel

val userListDiffUtil = object : RecyclerListDiffUtil<UserModel> {

    override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem.login == newItem.login
    }

    override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem.id == newItem.id && oldItem.nodeId == newItem.nodeId
    }
}