package com.loskon.githubapi.app.presentation.screens.userlist.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.loskon.githubapi.R
import com.loskon.githubapi.app.base.extension.view.setDebounceClickListener
import com.loskon.githubapi.app.base.widget.recyclerview.RecyclerDiffUtil
import com.loskon.githubapi.databinding.ItemUserCardBinding
import com.loskon.githubapi.domain.model.UserModel
import com.loskon.githubapi.utils.ImageLoader
import com.loskon.githubapi.viewbinding.viewBinding

/**
 * Адаптер для работы со списком пользователей
 */
class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    private var list: List<UserModel> = emptyList()

    private var onItemClickListener: ((UserModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        return UserListViewHolder(parent.viewBinding(ItemUserCardBinding::inflate))
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val user = list[position]

        with(holder.binding) {
            user.apply {
                ImageLoader.loadImage(ivUserCard, avatarUrl)
                tvUserCardLogin.text = login
                tvUserCardId.text = tvUserCardId.context.getString(R.string.user_id, id)
                cardViewUser.setDebounceClickListener { onItemClickListener?.invoke(this) }
            }
        }
    }

    override fun getItemCount(): Int = list.size

    fun setUsers(list: List<UserModel>) {
        val diffUtil = RecyclerDiffUtil(this.list, list, userListDiffUtil)
        val diffResult = DiffUtil.calculateDiff(diffUtil, false)
        this.list = list
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickListener(onItemClickListener: ((UserModel) -> Unit)?) {
        this.onItemClickListener = onItemClickListener
    }

    class UserListViewHolder(val binding: ItemUserCardBinding) : RecyclerView.ViewHolder(binding.root)
}