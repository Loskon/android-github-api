package com.loskon.githubapi.app.features.userlist.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.loskon.githubapi.R
import com.loskon.githubapi.base.extension.view.setDebounceClickListener
import com.loskon.githubapi.base.widget.recyclerview.RecyclerDiffUtil
import com.loskon.githubapi.databinding.ItemUserCardBinding
import com.loskon.githubapi.network.model.UserModel
import com.loskon.githubapi.utils.ImageLoader
import com.loskon.githubapi.viewbinding.viewBinding

/**
 * Адаптер для работы со списком пользователей
 */
class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    private var clickListener: ((UserModel) -> Unit)? = null
    private var list: List<UserModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        return UserListViewHolder(parent.viewBinding(ItemUserCardBinding::inflate))
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val user = list[position]

        with(holder.binding) {
            ImageLoader.loadImage(user.avatarUrl, ivUserCard)
            tvUserCardLogin.text = user.login
            tvUserCardGrade.text = tvUserCardGrade.context.getString(R.string.user_id, user.id)
            cardViewUser.setDebounceClickListener { clickListener?.invoke(user) }
        }
    }

    override fun getItemCount(): Int = list.size

    fun setUsers(newList: List<UserModel>) {
        val diffUtil = RecyclerDiffUtil(list, newList, userListDiffUtil)
        val diffResult = DiffUtil.calculateDiff(diffUtil, false)
        list = newList
        diffResult.dispatchUpdatesTo(this)
    }

    fun setItemClickListener(newClickListener: ((UserModel) -> Unit)?) {
        clickListener = newClickListener
    }

    inner class UserListViewHolder(val binding: ItemUserCardBinding) : RecyclerView.ViewHolder(binding.root)
}