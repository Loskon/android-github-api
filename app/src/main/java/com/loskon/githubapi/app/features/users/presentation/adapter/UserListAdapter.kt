package com.loskon.githubapi.app.features.users.presentation.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loskon.githubapi.base.extension.view.setDebounceClickListener
import com.loskon.githubapi.databinding.ItemUserCardBinding
import com.loskon.githubapi.network.glide.ImageLoader
import com.loskon.githubapi.network.retrofit.model.UserModel
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
            tvUserCardGrade.text = user.id.toString()
            cardViewUser.setDebounceClickListener { clickListener?.invoke(user) }
        }
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun setUsers(newList: List<UserModel>) {
        list = newList
        notifyDataSetChanged()
    }

    fun setItemClickListener(newClickListener: ((UserModel) -> Unit)?) {
        clickListener = newClickListener
    }

    inner class UserListViewHolder(val binding: ItemUserCardBinding) : RecyclerView.ViewHolder(binding.root)
}