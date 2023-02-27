package com.loskon.features.userlist.presentation

import com.loskon.base.widget.recyclerview.RecyclerListDiffUtil
import com.loskon.features.model.UserModel

val userListDiffUtil = object : RecyclerListDiffUtil<UserModel> {

    override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem.login == newItem.login
    }

    override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem.id == newItem.id && oldItem.nodeId == newItem.nodeId
    }
}